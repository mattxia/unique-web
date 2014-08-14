package org.unique.ioc.impl;

import java.util.Set;

import org.unique.ioc.BeanType;
import org.unique.ioc.Container;

/**
 * 获取bean的工厂
 * 
 * @author：rex
 * @create_time：2014-6-26
 * @version：V1.0
 */
@SuppressWarnings("unchecked")
public class BeanFactory {
    
    
    private static Container container;
    
    public static void init(Container container_) {
        container = container_;
    }
    
    /**
     * 根据类名获取bean实例
     * 
     * @author：rex
     * @param name
     * @param beanType SINGLE：单例方式，默认;NEW：新创建一个bean对象
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object getBean(String name, BeanType beanType) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (null == beanType) {
            return container.getBean(name);
        }
        if (beanType == BeanType.SINGLE) {
            return container.getBean(name);
        }
        return Class.forName(name).newInstance();
    }

    /**
     * 根据class获取bean实例
     * 
     * @author：rex
     * @param type
     * @param beanType SINGLE：单例方式，默认;NEW：新创建一个bean对象
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object getBean(Class<?> type, BeanType beanType) throws Exception {
        if (null == beanType) return container.getBean(type);
        if (beanType == BeanType.SINGLE) return container.getBean(type);
        return type.newInstance();
    }

    /**
     * 获取容器所有对象名
     * 
     * @author：rex
     * @return
     */
    public static Set<String> getBeanNames() {
        return (Set<String>) container.getBeanNames();
    }

    /**
     * 获取容器所有对象
     * 
     * @author：rex
     * @return
     */
    public static Object[] getBeans() {
        return container.getBeans().toArray();
    }

}
