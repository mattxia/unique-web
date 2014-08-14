package com.demo.inteceptor;

import org.apache.log4j.Logger;
import org.unique.web.core.ActionInvocation;
import org.unique.web.interceptor.Interceptor;

public class BBBBB implements Interceptor {

    private Logger logger = Logger.getLogger(BBBBB.class);
    
	@Override
	public void intercept(ActionInvocation ai) {
		ai.invoke();
		logger.info("bbbbb");
	}

}
