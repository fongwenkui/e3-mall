package cn.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.sso.service.LoginService;

/**
 * 登陆操作Controller
 * @author fong
 *
 */
@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;

	/**
	 * 到登陆页面
	 * @return
	 */
	@RequestMapping("/page/login")
	public String toLogin(String redirect,Model model){
		model.addAttribute("redirect",redirect);
		return "login";
	}
	
	/**
	 * 登陆
	 */
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public E3Result login(String username,String password,HttpServletRequest request,HttpServletResponse response){
		E3Result e3Result = loginService.login(username, password);
		if (e3Result.getData()!=null) {
			//将服务层传递过来的token添加进客户端的cookie中
			CookieUtils.setCookie(request, response, TOKEN_KEY, e3Result.getData().toString(), 1800);
		}
		return e3Result;	
	}
	
	
}
