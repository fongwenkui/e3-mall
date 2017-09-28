package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.zookeeper.server.FinalRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REDIS_ITEM_PRE}")
	private String REDIS_ITEM_PRE;
	
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource 
	private Destination topicDestination;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Override
	public TbItem findItemById(long id) {
		//查找redis缓存
		try {
			String json = jedisClient.get(REDIS_ITEM_PRE+":"+id+":BASE");
			if (json!=null) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// itemMapper.selectByPrimaryKey(id);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			try {
				//添加缓存
				jedisClient.set(REDIS_ITEM_PRE+":"+id+":BASE", JsonUtils.objectToJson(list.get(0)));
				//设置缓存过期时间
				jedisClient.expire(REDIS_ITEM_PRE+":"+id+":DESC", 3600);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			return list.get(0);
			
		}
		return null;
	}

	@Override
	public EasyUiDataGridResult findItemListPage(Integer page, Integer rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		// 创建一个返回值对象
		EasyUiDataGridResult result = new EasyUiDataGridResult();
		result.setRows(list);
		// 取分页结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		// 取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}

	@Override
	public E3Result insertItem(TbItem tbItem, String desc) {
		try {
			if (tbItem.getCid()==null) {
				return E3Result.build(500, "请选择分类");
			}
			// 随机生成商品id
			final long id = IDUtils.genItemId();
			tbItem.setId(id);
			// 1-正常，2-下架，3-删除
			tbItem.setStatus((byte) 1);
			Date date = new Date();
			tbItem.setCreated(date);
			tbItem.setUpdated(date);
			
			itemMapper.insert(tbItem);
			// 手动添加商品描述
			TbItemDesc tbItemDesc = new TbItemDesc();
			tbItemDesc.setItemId(id);
			tbItemDesc.setItemDesc(desc);
			tbItemDesc.setCreated(date);
			tbItemDesc.setCreated(date);
			tbItemDescMapper.insert(tbItemDesc);
			//添加完后,使用ActiveMq向search服务层发送信息,通知同步商品索引库
			jmsTemplate.send(topicDestination,new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					//发送消息
					TextMessage textMessage = session.createTextMessage("insert:"+id);
					return textMessage;
				}
			});
			// 返回E3Result
			E3Result e3Result = new E3Result(null);
			return e3Result;
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, "添加失败");
		}
		
		
		
		

	}

	@Override
	public E3Result updateItem(TbItem tbItem, String desc) {
		final Long id = tbItem.getId();
		tbItem.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(tbItem);
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemDesc(desc);
		tbItemDescMapper.updateByPrimaryKeyWithBLOBs(tbItemDesc);
		//更新完后,使用ActiveMq向search服务层发送信息,通知同步商品索引库
		jmsTemplate.send(topicDestination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送消息
				TextMessage textMessage = session.createTextMessage("update:"+id);
				return textMessage;
			}
		});
		E3Result e3Result = new E3Result(null);
		return e3Result;
	}

	@Override
	public E3Result deleteItemById(final Long ids) {
		itemMapper.deleteByPrimaryKey(ids);
		tbItemDescMapper.deleteByPrimaryKey(ids);
		//删除完后,使用ActiveMq向search服务层发送信息,通知同步商品索引库
				jmsTemplate.send(topicDestination,new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						//发送消息
						TextMessage textMessage = session.createTextMessage("delete:"+ids);
						return textMessage;
					}
				});
		E3Result e3Result = new E3Result(null);
		return e3Result;
	}

	@Override
	public E3Result instock(Long ids) {
		// 查询
		TbItem tbItem = itemMapper.selectByPrimaryKey(ids);
		if (tbItem.getStatus()==(byte)2) {
			return new E3Result(null);
		}
		// 1-正常，2-下架，3-删除
		tbItem.setStatus((byte) 2);
		TbItemExample te = new TbItemExample();
		Criteria criteria = te.createCriteria();
		criteria.andIdEqualTo(ids);
		itemMapper.updateByExampleSelective(tbItem, te);
		E3Result e3Result = new E3Result(null);
		return e3Result;
	}

	@Override
	public E3Result reshelf(Long ids) {
		// 查询
		TbItem tbItem = itemMapper.selectByPrimaryKey(ids);
		if (tbItem.getStatus()==(byte)1) {
			return new E3Result(null);
		}
		// 1-正常，2-下架，3-删除
		tbItem.setStatus((byte) 1);
		TbItemExample te = new TbItemExample();
		Criteria criteria = te.createCriteria();
		criteria.andIdEqualTo(ids);
		itemMapper.updateByExampleSelective(tbItem, te);
		E3Result e3Result = new E3Result(null);
		return e3Result;
	}
}
