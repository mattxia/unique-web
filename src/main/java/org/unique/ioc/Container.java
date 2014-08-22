package org.unique.ioc;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * bean container
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public interface Container {

    Object getBean(String name);

    Object getBean(Class<?> type);

    Set<?> getBeanNames();

    Collection<?> getBeans();

    boolean hasBean(Class<?> clazz);

    boolean hasBean(String name);

    Object registBean(Class<?> clazz);

    void initWired();

    boolean isRegister(Annotation[] annotations);
    
    Map<String, Object> getBeanMap();
}
