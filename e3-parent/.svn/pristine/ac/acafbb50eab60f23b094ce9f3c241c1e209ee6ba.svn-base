package cn.e3mall.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/**
 * 全局异常处理
 * @author fong
 *
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver{

	private static final Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionResolver.class); 	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {
		//打印异常到控制台
		e.printStackTrace();
		//写日记
		logger.debug("测试输出日记。。。");
		logger.info("系统发生异常。。。");
		logger.error("系统发生异常",e);
		//发邮件 mail
		//发短信 第三方接口webservice
		
		//显示异常页面
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}
	
}
