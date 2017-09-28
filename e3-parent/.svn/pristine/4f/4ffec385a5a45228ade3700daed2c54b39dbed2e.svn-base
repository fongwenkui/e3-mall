package cn.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.sso.service.TokenService;

@Controller
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	/**
	 * 根据cookie(token)得到用户信息
	 * @param token
	 * @return
	 */
	//produces:相当于 request.setContent("application/json;charset=utf-8");
	@RequestMapping(value="user/token/{token}",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String getUserByToken(@PathVariable("token") String token,String callback){
		E3Result e3Result = tokenService.GetUserByToken(token);
		if (callback!=null) {
			//响应的是一个js语句
			String json=callback+"("+JsonUtils.objectToJson(e3Result)+");";
			return json;
		}
		return JsonUtils.objectToJson(e3Result);
	}
	
}
