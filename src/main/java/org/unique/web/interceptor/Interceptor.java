package org.unique.web.interceptor;

import org.unique.web.core.ActionInvocation;

/**
 * 拦截器接口
 * @author：rex
 * @create_time：2014-6-25 下午3:08:16  
 * @version：V1.0
 */
public interface Interceptor {

	void intercept(ActionInvocation ai) throws Exception;

	enum CLEAR {
		UPPER, ALL
	}
}
