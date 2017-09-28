package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbUser;

public interface RegisterService {
	/**
	 * 检验用户名或手机号是否存在
	 * @param value
	 * @param type	1.用户名		2.手机号
	 * @return
	 */
	E3Result checkData(String value,Integer type);
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	E3Result register(TbUser user);
}
