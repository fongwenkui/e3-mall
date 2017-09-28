package cn.e3mall.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.sso.service.TokenService;

public class OrderInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	@Value("${LOGIN_PAGE_URL}")
	private String LOGIN_PAGE_URL;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 如果已经登录放行
		// 如果没有登录从定向到登录页面并带上一个/order/order-cart.html的redirect参数,当用户登录成功后跳转到订单页面
		String token = CookieUtils.getCookieValue(request, "token");
		if (StringUtils.isBlank(token)) {
				//从定向到登录页面
				response.sendRedirect(LOGIN_PAGE_URL+"?redirect="+request.getRequestURL());
				return false;
			}
		//查sso中用户是否已经过期
		E3Result e3Result = tokenService.GetUserByToken(token);
		if (e3Result.getStatus()!=200) {
			//从定向到登录页面
			response.sendRedirect(LOGIN_PAGE_URL+"?redirect="+request.getRequestURL());
			return false;
		}
		request.setAttribute("user", e3Result.getData());
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
