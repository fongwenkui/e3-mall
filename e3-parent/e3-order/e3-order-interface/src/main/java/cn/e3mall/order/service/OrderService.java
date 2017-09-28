package cn.e3mall.order.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

public interface OrderService {
	
	/**
	 * 创建订单,将数据插入三张订单表中(物理，明细，订单)
	 * @param orderInfo
	 * @return
	 */
	E3Result createOrder(OrderInfo orderInfo);
	
}
