package org.unique.web.interceptor;

import org.unique.web.core.ActionInvocation;

/**
 * 拦截器接口
 * @author：rex
 * @create_time：2014-6-25 下午3:08:16  
 * @version：V1.0
 */
public abstract class AbstractInterceptor implements Interceptor {

	public void before(ActionInvocation ai) throws Exception {
	}

	public void after(ActionInvocation ai) throws Exception {
	}

	public abstract void intercept(ActionInvocation ai) throws Exception;
}
