package org.unique.web.interceptor;

import java.lang.reflect.Method;

import org.unique.web.core.Controller;

/**
 * 拦截器工厂接口
 * @author：rex
 * @create_time：2014-6-25 下午3:06:36  
 * @version：V1.0
 */
public interface InterceptorFactory {
	
	void addToInterceptorsMap(Interceptor[] interceptors);
	
	Interceptor[] getDefaultInterceptors();
	
	Interceptor[] getControllerInterceptors(Class<? extends Controller> controller);
	
	Interceptor[] getMethodInterceptors(Method method);
	
	Interceptor[] getActionInterceptors(Interceptor[] defaultInters, Interceptor[] controllerInters, Class<? extends Controller> controllerClass, Interceptor[] methodInters, Method method);
	
}
