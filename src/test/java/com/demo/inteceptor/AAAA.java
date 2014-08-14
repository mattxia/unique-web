package com.demo.inteceptor;

import org.apache.log4j.Logger;
import org.unique.web.core.ActionInvocation;
import org.unique.web.interceptor.Interceptor;

public class AAAA implements Interceptor {

    private Logger logger = Logger.getLogger(AAAA.class);
    
	@Override
	public void intercept(ActionInvocation ai) {
	    logger.info("11111111");
		ai.invoke();
		logger.info("22222222");
	}

}
