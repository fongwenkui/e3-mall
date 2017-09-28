package cn.e3mall.cart.service;

import java.util.List;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbItem;

public interface CartService {
	
	 
	/**
	 * 添加购物车业务逻辑
	 * @param userId
	 * @param itemId
	 * @param num
	 * @return
	 */
	E3Result addCart(long userId,long itemId,int num);
	
	/**
	 * 合并cookie中的和redis中的购物车
	 * @param userId
	 * @param itemList
	 * @return
	 */
	E3Result margeCart(long userId,List<TbItem> itemList);
	
	/**
	 * 通过用户id获得购物车列表
	 * @param userId
	 * @return
	 */
	List<TbItem> getItemListByUserId(long userId);
	
	/**
	 * 更新商品数量
	 * @param userId
	 * @param itemId
	 * @param num
	 * @return
	 */
	E3Result updateNum(long userId,long itemId,int num);
	
	/**
	 * 清空购物车
	 * @param userId
	 * @return
	 */
	E3Result clearCartItem(long userId);
	
	E3Result deleteCartItem(long userId,long itemId);
	
}
