package cn.e3mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_ITEM_DESC_PRE}")
	private String REDIS_ITEM_DESC_PRE;
	
	@Override
	public E3Result findItemDescById(long id) {
		try {
			try {
				//查redis缓存
				String json = jedisClient.get(REDIS_ITEM_DESC_PRE+":"+id+":DESC");
				if (json!=null) {
					TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
					return new E3Result(tbItemDesc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(id);
			if (tbItemDesc!=null) {
				//status:200 data:... msg:ok
				E3Result e3Result=new E3Result(tbItemDesc);
				try {
					//添加缓存
					jedisClient.set(REDIS_ITEM_DESC_PRE+":"+id+":DESC", JsonUtils.objectToJson(tbItemDesc));
					//设置过期时间
					jedisClient.expire(REDIS_ITEM_DESC_PRE+":"+id+":DESC", 3600);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return e3Result;
			}
		} catch (Exception e) {
			//status:null null null
			E3Result e3Result=new E3Result();
			return e3Result;
		}
		//status:null null null
		E3Result e3Result=new E3Result();
		return e3Result;
	}

	@Override
	public void insertItemDesc(TbItemDesc itemDesc ) {
		itemDescMapper.insert(itemDesc);
	}
	
	

}
