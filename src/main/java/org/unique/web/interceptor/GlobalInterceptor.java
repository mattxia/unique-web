package org.unique.web.interceptor;

import org.unique.web.core.ActionInvocation;
import org.unique.web.interceptor.Interceptor;

/**
 * 全局拦截器，继承该类并实现intercept方法
 * @author：rex
 * @create_time：2014-6-25 下午3:08:58  
 * @version：V1.0
 */
public abstract class GlobalInterceptor implements Interceptor{

	public abstract void intercept(ActionInvocation ai);

}
