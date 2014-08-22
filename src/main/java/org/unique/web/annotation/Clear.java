package org.unique.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * clear interceptor
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Clear {
	
	ClearLayer value();
	
	public enum ClearLayer{
		UPPER,
		ALL
	}
}

