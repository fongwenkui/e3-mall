package cn.e3mall.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.mapper.TbOrderItemMapper;
import cn.e3mall.mapper.TbOrderMapper;
import cn.e3mall.mapper.TbOrderShippingMapper;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private JedisClient jedisClient;
	//Order表id
	@Value("${ORDER_ID_KEY}")
	private String ORDER_ID_KEY;
	//初始id
	@Value("${ORDER_ID_INIT}")
	private String ORDER_ID_INIT;
	//OrderItem表id
	@Value("$(ORDERITEM_ID_KEY)")
	private String ORDERITEM_ID_KEY;
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	

	@Override
	public E3Result createOrder(OrderInfo orderInfo) {
		// 因为涉及事务,所以不应该使用try&catch处理异常,因为如果抛异常就会rollback，而try&catch就不会rollback
		// 补全数据
		// 订单id用为要返回用户,所以用redis的incr作为订单的id
		Boolean exists = jedisClient.exists(ORDER_ID_KEY);
		//如果第一次商城订单id为订单id设置初始值
		if (!exists) {
			jedisClient.set(ORDER_ID_KEY, ORDER_ID_INIT);
		}
		String orderId = jedisClient.incr(ORDER_ID_KEY).toString();
		orderInfo.setOrderId(orderId);
		//状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭'
		orderInfo.setStatus(1);
		//时间
		Date date=new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		//order表
		orderMapper.insert(orderInfo);
		
		//orderItem表
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			String orderItemId = jedisClient.incr(ORDERITEM_ID_KEY).toString();
			//补全数据
			tbOrderItem.setId(orderItemId);
			//属于哪张订单
			tbOrderItem.setOrderId(orderId);
			orderItemMapper.insert(tbOrderItem);
		}
		
		//orderShipping
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		//补全数据
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		
		return E3Result.ok(orderId);
	}

}
