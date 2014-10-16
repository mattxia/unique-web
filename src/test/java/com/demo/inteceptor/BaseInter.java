package com.demo.inteceptor;

import javax.servlet.http.HttpServletRequest;

import org.unique.web.annotation.Intercept;
import org.unique.web.core.ActionContext;
import org.unique.web.core.ActionInvocation;
import org.unique.web.interceptor.Interceptor;

@Intercept
public class BaseInter implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) throws Exception {
		System.out.println("aaaa");
		HttpServletRequest request = ai.getController().getRequest();
		String basePath = request.getContextPath();
	    request.setAttribute("base", basePath);
		ai.invoke();
		System.out.println("bbbbb");
	}
}
