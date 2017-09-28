package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;

	@Override
	public EasyUiDataGridResult getContentPageByCategoryId(Long categoryId, Integer page, Integer size) {
		//设置分页信息
		PageHelper.startPage(page, size);
		//创建条件
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//执行查询
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		//使用mybatis分页插件
		PageInfo<TbContent> pageInfo=new PageInfo<>(list);
		//创建EasyUiDataGridResult
		EasyUiDataGridResult easyUiDataGridResult=new EasyUiDataGridResult();
		//手动封装
		easyUiDataGridResult.setRows(list);
		//封装总记录数
		easyUiDataGridResult.setTotal(pageInfo.getTotal());
		return easyUiDataGridResult;
	}

	@Override
	public E3Result saveContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insert(content);
		//添加内容后，更新同步缓存(删除缓存)
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		return new E3Result(content.getContent());
	}

	@Override
	public List<TbContent> findContentById(long category_id) {
		//查询是否有缓存
		try {
			String json = jedisClient.hget(CONTENT_LIST,category_id+"");
			if (StringUtils.isNotBlank(json)) {
				return JsonUtils.jsonToList(json, TbContent.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//条件
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(category_id);
		//执行查询
		List<TbContent> list = contentMapper.selectByExample(example);
		//添加到缓存中
		try {
			jedisClient.hset(CONTENT_LIST,category_id+"",JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public E3Result updateContent(TbContent content) {
		try {
			contentMapper.updateByPrimaryKeySelective(content);
			//更新内容后，更新同步缓存(删除缓存)
			jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
			return new E3Result(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public E3Result deleteContentById(Long ids) {
		try {
			TbContent tbContent = contentMapper.selectByPrimaryKey(ids);
			contentMapper.deleteByPrimaryKey(ids);
			//删除内容后，更新同步缓存(删除缓存)
			jedisClient.hdel(CONTENT_LIST, tbContent.getCategoryId().toString());
			return new E3Result(null);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	

	

}
