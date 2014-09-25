package org.unique.web.interceptor;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.unique.common.tools.ClassHelper;
import org.unique.common.tools.CollectionUtil;
import org.unique.web.annotation.Intercept;
import org.unique.web.core.Const;

/**
 * 拦截器工厂接口
 * @author：rex
 * @create_time：2014-6-25 下午3:06:36  
 * @version：V1.0
 */
public class InterceptorFactory {

	private static final List<Interceptor> interceptorList = CollectionUtil.newArrayList();

	/**
	 * 初始化拦截器
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void buildInterceptor() throws InstantiationException, IllegalAccessException {
		String interPack = Const.CONST_MAP.get("unique.globalInterceptor");
		if (StringUtils.isNoneBlank(interPack)) {
			String pack = interPack.trim();
			List<Interceptor> interceptorListTemp = CollectionUtil.newArrayList();
			Set<Class<?>> classes = ClassHelper.scanPackage(pack);
			for (Class<?> clazz : classes) {
				if (Interceptor.class.isAssignableFrom(clazz) && null != clazz.getAnnotation(Intercept.class)) {
					interceptorListTemp.add((Interceptor) clazz.newInstance());
				}
			}
			interceptorList.addAll(interceptorListTemp);
			for (Interceptor interceptor : interceptorListTemp) {
				if (null != interceptor.getClass().getAnnotation(Intercept.class)) {
					int v = interceptor.getClass().getAnnotation(Intercept.class).loadOnStartup();
					interceptorList.set(v, interceptor);
				}
			}
		}
	}

	public static List<Interceptor> getInterceptors() {
		return interceptorList;
	}
}
