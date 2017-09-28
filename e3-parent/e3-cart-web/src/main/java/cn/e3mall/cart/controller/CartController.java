package cn.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private ItemService itService;

	/**
	 * 添加购物车
	 * 
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		// 判断request.getAttribute("user");
		TbUser user = (TbUser) request.getAttribute("user");
		// 如果user不等于null
		if (user != null) {
			// 添加进redis中
			// 调用服务层中的addCart()得到E3Result
			E3Result e3Result = cartService.addCart(user.getId(), itemId, num);
			return "cartSuccess";
		}
		// 如果user不等于null
		String json = CookieUtils.getCookieValue(request, "cart", true);
		if (StringUtils.isNotBlank(json)) {
			// 添加进cookie中
			// 先从cookie中取cart,
			List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
			// 判断商品在列表中是否存在
			boolean flag = true;
			for (TbItem tbItem : itemList) {
				if (tbItem.getId() == itemId.longValue()) {
					flag = false;
					// 如果有重复的商品,数量叠加
					tbItem.setNum(num + tbItem.getNum());
					break;
				}
			}
			// 如果没有在列表中
			if (flag) {
				// 根据商品id查询数据库,从数据库中查询得到TbItem
				TbItem tbItem = itService.findItemById(itemId);
				// TbItem.setNum(num);整合数量
				tbItem.setNum(num);
				itemList.add(tbItem);
			}
			// 重新写回cookie中
			CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(itemList), true);
		} else {
			// 如果为空,表示购物车没有东西
			// 根据商品id查询数据库,从数据库中查询得到TbItem
			TbItem tbItem = itService.findItemById(itemId);
			// TbItem.setNum(num);整合数量
			tbItem.setNum(num);
			// List<TbItem>.add(TbItem)
			List<TbItem> itemList = new ArrayList<>();
			itemList.add(tbItem);
			// 将List<TbItem>转成json字符串写进客户端cookie中
			CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(itemList), true);
		}
		// 到添加成功页面
		return "cartSuccess";
	}

	/**
	 * 商品展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response) {
		// 判断request.getAttribute("user");
		TbUser user = (TbUser) request.getAttribute("user");
		String json = CookieUtils.getCookieValue(request, "cart", true);
		// 如果user不等于null
		if (user != null) {
			// 查询cookie中的cart
			if (StringUtils.isNotBlank(json)) {
				List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
				// 查询到的value合并添加到redis中
				E3Result e3Result = cartService.margeCart(user.getId(), itemList);
				if (e3Result.getStatus() == 200) {
					// 合并成功
					// 清除客户端的cookie
					CookieUtils.deleteCookie(request, response, "cart");
				}
			}
			// 得到当前用户所有的购物项
			List<TbItem> cart = cartService.getItemListByUserId(user.getId());
			request.setAttribute("cartList", cart);
		} else {
			// 没有登录
			if (StringUtils.isNotBlank(json)) {
				List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
				request.setAttribute("cartList", itemList);
			}
		}
		return "cart";
	}

	/**
	 * 更新商品数量
	 * 
	 * @param itemId
	 * @param num
	 * @param request
	 * @return
	 */
	@RequestMapping("cart/update/num/{itemId}/{num}")
	public String updateCart(@PathVariable("itemId") Long itemId, @PathVariable("num") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		// 判断request.getAttribute("user");
		TbUser user = (TbUser) request.getAttribute("user");
		String json = CookieUtils.getCookieValue(request, "cart", true);
		if (user != null) {
			// 更新redis中的
			E3Result e3Result = cartService.updateNum(user.getId(), itemId, num);
		} else {
			// 更新cookie中
			if (json != null) {
				List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
				for (TbItem tbItem : itemList) {
					if (tbItem.getId() == itemId.longValue()) {
						tbItem.setNum(num);
						break;
					}
				}
				// 重新写回cookie中
				CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(itemList), true);
			}

		}
		return "redirect:/cart/cart.html";
	}

	@RequestMapping("cart/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 判断request.getAttribute("user");
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.deleteCartItem(user.getId(), itemId);
			//重定向到展示购物车页面
			return "redirect:/cart/cart.html";
		}
		//先取找到对应的商品删除退出马上退出循环
		String json = CookieUtils.getCookieValue(request, "cart", true);
		if (StringUtils.isNotBlank(json)) {
			List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
			for (TbItem tbItem : itemList) {
				if (tbItem.getId() == itemId.longValue()) {
					itemList.remove(tbItem);
					break;
				}
			}
			// 重新写回cookie中
			CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(itemList), true);
		}
		//重定向到展示购物车页面
		return "redirect:/cart/cart.html";
	}

}
