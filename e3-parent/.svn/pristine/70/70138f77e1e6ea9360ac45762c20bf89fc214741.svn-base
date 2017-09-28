package cn.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

public class CartInterceptor implements HandlerInterceptor{

	@Autowired
	private TokenService tokenService;
	
	/**
	 * 创建一个拦截器查询token是否有值
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2) throws Exception {
		//查询token是否有值
		String token = CookieUtils.getCookieValue(request, "token");
		if(token!=null){
			//如果有
			//通过jedis查询是否已经过期
			TbUser user = (TbUser) tokenService.GetUserByToken(token).getData();
			if (user==null) {
				//如果没有过期就是已经登录
				return true;
			}
			request.setAttribute("user",user);
		}
		//如果没就是没有登录
		//放行
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	

}
