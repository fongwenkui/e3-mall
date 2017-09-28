package cn.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;

/**
 * 注册功能Controller
 * @author fong
 *
 */
@Controller
public class RegisterController {
	
	@Autowired
	private RegisterService registerService;
	
	/**
	 * 到注册页面
	 * @return
	 */
	@RequestMapping("/page/register")
	public String toRegister(){
		return "register";
	}
	
	/**
	 * 校验用户名或者手机号是否存在
	 * @param value
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/user/check/{value}/{type}")
	@ResponseBody
	public E3Result checkData(@PathVariable String value,@PathVariable Integer type){
		//type:1.用户名	2.手机号
		E3Result e3Result = registerService.checkData(value, type);
		return e3Result;
	}
	
	/**
	 * 注册用户
	 * @return
	 */
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public E3Result register(TbUser user){
		E3Result e3Result = registerService.register(user);
		return e3Result;
	}
	
}
