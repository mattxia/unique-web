package org.unique.web.interceptor.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.unique.common.tools.CollectionUtil;
import org.unique.web.annotation.Clear;
import org.unique.web.annotation.Intercept;
import org.unique.web.annotation.Clear.ClearLayer;
import org.unique.web.core.Controller;
import org.unique.web.interceptor.Interceptor;
import org.unique.web.interceptor.InterceptorFactory;

/**
 * 拦截器工厂实现
 * @author：rex
 * @create_time：2014-6-25 下午2:10:37  
 * @version：V1.0
 */
public class InterceptorFactoryImpl implements InterceptorFactory {

	private final List<Interceptor> interceptorList = CollectionUtil.newArrayList();
	private static final Interceptor[] NULL_INTERCEPTOR_ARRAY = new Interceptor[0];
	private Map<Class<Interceptor>, Interceptor> intersMap = CollectionUtil.newHashMap();
	
	public InterceptorFactoryImpl(Interceptor gloableInterceptor) {
		if(null != gloableInterceptor){
			this.interceptorList.add(gloableInterceptor);
		}
	}
	
	/**
	 * 添加一个拦截器
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addToInterceptorsMap(Interceptor[] defaultInters) {
		for (Interceptor inter : defaultInters) {
			intersMap.put((Class<Interceptor>)inter.getClass(), inter);
		}
	}

	/**
	 * 获取控制器的拦截器
	 */
	@Override
	public Interceptor[] getControllerInterceptors(Class<? extends Controller> controller) {
		Intercept before = controller.getAnnotation(Intercept.class);
		return before != null ? createInterceptors(before) : NULL_INTERCEPTOR_ARRAY;
	}

	/**
	 * 获取方法上的拦截器
	 */
	@Override
	public Interceptor[] getMethodInterceptors(Method method) {
		Intercept before = method.getAnnotation(Intercept.class);
		return before != null ? createInterceptors(before) : NULL_INTERCEPTOR_ARRAY;
	}

	/**
	 * 获取action的拦截器
	 */
	@Override
	public Interceptor[] getActionInterceptors(Interceptor[] defaultInters,
			Interceptor[] controllerInters, Class<? extends Controller> controllerClass, Interceptor[] methodInters, Method method) {
		
		ClearLayer controllerClearType = getControllerClearType(controllerClass);
		if (controllerClearType != null) {
			defaultInters = NULL_INTERCEPTOR_ARRAY;
		}
		
		ClearLayer methodClearType = getMethodClearType(method);
		if (methodClearType != null) {
			controllerInters = NULL_INTERCEPTOR_ARRAY;
			if (methodClearType == ClearLayer.ALL) {
				defaultInters = NULL_INTERCEPTOR_ARRAY;
			}
		}
		
		int size = defaultInters.length + controllerInters.length + methodInters.length;
		Interceptor[] result = (size == 0 ? NULL_INTERCEPTOR_ARRAY : new Interceptor[size]);
		
		int index = 0;
		for (int i=0; i<defaultInters.length; i++) {
			result[index++] = defaultInters[i];
		}
		for (int i=0; i<controllerInters.length; i++) {
			result[index++] = controllerInters[i];
		}
		for (int i=0; i<methodInters.length; i++) {
			result[index++] = methodInters[i];
		}
		
		return result;
	}
	
	private ClearLayer getMethodClearType(Method method) {
		Clear clearInterceptor = method.getAnnotation(Clear.class);
		return clearInterceptor != null ? clearInterceptor.value() : null ;
	}
	
	private ClearLayer getControllerClearType(Class<? extends Controller> controllerClass) {
		Clear clearInterceptor = controllerClass.getAnnotation(Clear.class);
		return clearInterceptor != null ? clearInterceptor.value() : null ;
	}
	
	/**
	 * 默认拦截器
	 */
	@Override
	public Interceptor[] getDefaultInterceptors() {
		Interceptor[] result = interceptorList.toArray(new Interceptor[interceptorList.size()]);
		return result == null ? new Interceptor[0] : result;
	}
	
	/**
	 * 创建一个拦截器
	 * @author：rex  
	 * @param beforeAnnotation
	 * @return
	 */
	private Interceptor[] createInterceptors(Intercept beforeAnnotation) {
		Interceptor[] result = null;
		@SuppressWarnings("unchecked")
		Class<Interceptor>[] interceptors = (Class<Interceptor>[]) beforeAnnotation.value();
		if (null != interceptors && interceptors.length > 0) {
			result = new Interceptor[interceptors.length];
			for (int i=0; i<result.length; i++) {
				result[i] = intersMap.get(interceptors[i]);
				if (result[i] == null) {
					try {
						result[i] = (Interceptor)interceptors[i].newInstance();
						intersMap.put(interceptors[i], result[i]);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return result;
	}
	
}
