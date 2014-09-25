package com.demo.inteceptor;

import org.unique.web.annotation.Intercept;
import org.unique.web.core.ActionInvocation;
import org.unique.web.interceptor.Interceptor;

@Intercept(loadOnStartup=1)
public class BaseInter2 implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) throws Exception {
		System.out.println("cccc");
		ai.invoke();
		System.out.println("dddd");
	}
}
