package cn.e3mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;
	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public E3Result addCart(long userId, long itemId, int num) {
		//判断购物车中是否有此商品
		Boolean exists = jedisClient.hexists(REDIS_CART_PRE+":"+userId,itemId+"");
		if (exists) {
			//如果存在.数量相加
			String json = jedisClient.hget(REDIS_CART_PRE+":"+userId, itemId+"");
			if (StringUtils.isNotBlank(json)) {
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				item.setNum(item.getNum()+num);
				//重新写回redis中
				jedisClient.hset(REDIS_CART_PRE+":"+userId,  itemId+"", JsonUtils.objectToJson(item));
			}
		}else{
			//如果不存在,添加商品
			//从数据库中查询商品
			TbItem tbItem=itemMapper.selectByPrimaryKey(itemId);
			//设置购物车的数量
			tbItem.setNum(num);
			//设置图片
			tbItem.setImage(tbItem.getImage().split(",")[0]);
			jedisClient.hset(REDIS_CART_PRE+":"+userId, itemId+"", JsonUtils.objectToJson(tbItem));
		}
	return E3Result.ok();
	}

	
	@Override
	public E3Result margeCart(long userId, List<TbItem> itemList) {
		//遍历itemList
		for (TbItem tbItem : itemList) {
			//合并
			addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		return E3Result.ok();
	}


	@Override
	public List<TbItem> getItemListByUserId(long userId) {
		//获得redis中当前用户所有的hval
		List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE+":"+userId);
		List<TbItem> list=new ArrayList<>();
		//遍历将json转成TbItem对象
		for (String json : jsonList) {
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			list.add(tbItem);
		}
		return list;
	}


	@Override
	public E3Result updateNum(long userId,long itemId, int num) {
		String json = jedisClient.hget(REDIS_CART_PRE+":"+userId,itemId+"");
		if (StringUtils.isNotBlank(json)) {
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				if (itemId==tbItem.getId()) {
					tbItem.setNum(num);
					//重新写回去
;					jedisClient.hset(REDIS_CART_PRE+":"+userId, itemId+"", JsonUtils.objectToJson(tbItem));
					return E3Result.ok();
			}
		}
		return E3Result.build(201, "登录已过时,请重新登录!");
	}


	@Override
	public E3Result clearCartItem(long userId) {
		jedisClient.del(REDIS_CART_PRE+":"+userId);
		
		return E3Result.ok();
	}


	@Override
	public E3Result deleteCartItem(long userId, long itemId) {
		jedisClient.hdel(REDIS_CART_PRE+":"+userId, itemId+"");
		return E3Result.ok();
	}

	
	

}
