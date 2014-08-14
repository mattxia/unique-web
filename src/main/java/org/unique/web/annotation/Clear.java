package org.unique.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 清除拦截器
 * @author：rex
 * @create_time：2014-6-25 上午10:32:02  
 * @version：V1.0
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

