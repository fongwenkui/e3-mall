package cn.e3mall.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;

/**
 * 订单控制器
 * 
 * @author fong
 *
 */
@Controller
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;

	/**
	 * 展示订单页面
	 * 
	 * @return
	 */
	@RequestMapping("/order/order-cart")
	public String showOrder(HttpServletRequest request) {
		/*
		 * 展示订单页面前首先判断用户是否已经登录 需要写一个拦截器 如果已经登录放行
		 * 如果没有登录从定向到登录页面并带上一个/order/order-cart.html的redirect参数,当用户登录成功后跳转到订单页面
		 * 既然走到这个方法的一定是已经登录了 从用户中取用户信息,取用户id 查看cookie中是否有购物车,如果有,与redis的购物车合并
		 * 从redis中取购物车响应给用户
		 */

		// 从用户中取用户信息,取用户id
		TbUser user = (TbUser) request.getAttribute("user");
		// 查看cookie中是否有购物车,如果有,与redis的购物车合并
		String json = CookieUtils.getCookieValue(request, "cart", true);
		if (StringUtils.isNotBlank(json)) {
			// 合并
			E3Result e3Result = cartService.margeCart(user.getId(), JsonUtils.jsonToList(json, TbItem.class));
		}
		// 从redis中取购物车响应给用户
		List<TbItem> itemList = cartService.getItemListByUserId(user.getId());
		request.setAttribute("cartList", itemList);
		// 返回逻辑视图
		return "order-cart";
	}

	@RequestMapping("/order/create")
	public String createOrder(OrderInfo orderInfo, HttpServletRequest request) {
		/*
		 * 提交订单 将购物车当中的所有商品添加到数据库中 数据库中3张表(订单明细&订单表&订单物流)
		 * 页面传递过来的pojo，表现层需要用到,服务层也需要用到
		 * 不能放在common中，因为这个pojo需要继承TbOrder，就要需要依赖manger，而manager又依赖common造成循环依赖。
		 * 应该放在interface中
		 * 
		 * 表现层: 映射从页面中传递过来的OrderInfo 设置OrderInfo的user_id 调用服务层将数据插入数据库 服务层:
		 * 因为涉及事务,所以不应该使用try&catch处理异常,因为如果抛异常就会rollback，而try&catch就不会rollback
		 * 补全数据 订单id用为要返回用户,所以用redis的incr作为订单的id
		 */

		// 映射从页面中传递过来的OrderInfo
		TbUser user = (TbUser) request.getAttribute("user");
		// 设置OrderInfo的user_id
		orderInfo.setUserId(user.getId());
		// 调用服务层将数据插入数据库
		E3Result e3Result = orderService.createOrder(orderInfo);
		// 如果订单生成成功，需要删除购物车
		if (e3Result.getStatus() == 200) {
			// 清空购物车
			cartService.clearCartItem(user.getId());
		}
		// 把订单号传递给页面
		request.setAttribute("orderId", e3Result.getData());
		//金额
		System.out.println(orderInfo.getPayment()+","+orderInfo.getPostFee());
		request.setAttribute("payment", Double.parseDouble(orderInfo.getPayment())+Double.parseDouble(orderInfo.getPostFee()));
		return "success";
	}

}
