package org.unique.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为方法配置path
 * @author：rex
 * @create_time：2014-6-20 下午2:57:57  
 * @version：V1.0
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {
	
	public enum HttpMethod{ 
	    ALL, GET, POST, PUT, PATCH, DELETE, HEAD, TRACE, CONNECT, OPTIONS, BEFORE, AFTER
	};
	
	String value();
	
	HttpMethod method() default HttpMethod.ALL;
	
}
