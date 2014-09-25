package com.demo.inteceptor;

import org.unique.web.annotation.Intercept;
import org.unique.web.core.ActionInvocation;
import org.unique.web.interceptor.Interceptor;

@Intercept
public class BaseInter implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) throws Exception {
		System.out.println("aaaa");
		ai.invoke();
		System.out.println("bbbbb");
	}
}
