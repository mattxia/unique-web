package org.unique.ioc;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 存取bean的容器接口
 * 
 * @author: rex
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
