package com.demo.inteceptor;

import javax.servlet.http.HttpServletRequest;

import org.unique.ioc.annotation.Component;
import org.unique.web.core.ActionContext;
import org.unique.web.core.ActionInvocation;
import org.unique.web.interceptor.GlobalInterceptor;

/**
 * 全局拦截器demo
 * @author：rex
 * @create_time：2014-6-25 下午4:02:02  
 * @version：V1.0
 */
@Component
public class BaseInterceptor extends GlobalInterceptor{

	@Override
	public void intercept(ActionInvocation ai) {
		//System.out.println("全局before");
	    HttpServletRequest request = ActionContext.single().getHttpServletRequest();
	    request.setAttribute("BASE_PATH", request.getContextPath());
		ai.invoke();
		//System.out.println("全局after");
	}
	
}
