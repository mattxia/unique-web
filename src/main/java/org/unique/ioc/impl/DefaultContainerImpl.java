package org.unique.ioc.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.unique.common.tools.CollectionUtil;
import org.unique.ioc.Container;
import org.unique.ioc.annotation.Autowired;
import org.unique.ioc.annotation.Component;
import org.unique.ioc.annotation.Service;
import org.unique.web.annotation.Path;

/**
 * 默认的bean工厂实现
 * 
 * @author：rex
 */
public class DefaultContainerImpl implements Container {

    private static final Logger logger = Logger.getLogger(DefaultContainerImpl.class);

    // 保存bean对象的map
    private final Map<String, Object> beansMap = CollectionUtil.newHashMap();

    private DefaultContainerImpl() {
    }

    public Map<String, Object> getBeanMap() {
        return beansMap;
    }

    public static DefaultContainerImpl single() {
        return DefaultContainerHoder.single;
    }

    private static class DefaultContainerHoder {

        private static final DefaultContainerImpl single = new DefaultContainerImpl();
    }

    @Override
    public Object getBean(String name) {
        return beansMap.get(name);
    }

    @Override
    public Object getBean(Class<?> type) {
        Iterator<Object> it = this.beansMap.values().iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (type.isAssignableFrom(obj.getClass())) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public Set<?> getBeanNames() {
        return beansMap.keySet();
    }

    @Override
    public Collection<?> getBeans() {
        return beansMap.values();
    }

    @Override
    public boolean hasBean(Class<?> clz) {
        if (null != this.getBean(clz)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasBean(String name) {
        if (null != this.getBean(name)) {
            return true;
        }
        return false;
    }

    /**
     * 注册一个bean对象到容器里
     */
    @Override
    public Object registBean(Class<?> clazz) {
        String name = clazz.getCanonicalName();
        try {
            Object obj = null;
            if (!Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers())) {
                logger.debug("to load the class：" + name);
                obj = clazz.newInstance();
                beansMap.put(name, obj);
            } else {
                String implName = clazz.getName().substring(0, clazz.getName().lastIndexOf(".")) + ".impl." + clazz.getSimpleName() + "Impl";
                obj = Class.forName(implName).newInstance();
                beansMap.put(name, obj);
            }
            return obj;
        } catch (InstantiationException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error("class:" + clazz + " not found" + e.getMessage());
        }
        return null;
    }

    /**
     * 初始化注入
     */
    @Override
    public void initWired() {
        Iterator<Object> it = this.beansMap.values().iterator();
        try {
            while (it.hasNext()) {
                Object obj = it.next();
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    if (null != autowired) {
                        // 要注入的字段
                        Object wiredField = this.getBean(field.getType());
                        // 指定装配的类
                        if (autowired.value() != Class.class) {
                            wiredField = this.getBean(autowired.value());
                            // 容器有该类
                            if (null == wiredField) {
                                wiredField = this.registBean(autowired.value());
                            }
                        }
                        if (null == wiredField) {
                            throw new RuntimeException("Unable to load " + field.getType().getCanonicalName() + "！");
                        }
                        boolean accessible = field.isAccessible();
                        field.setAccessible(true);
                        field.set(obj, wiredField);
                        field.setAccessible(accessible);
                    }
                }
            }
        } catch (SecurityException e) {
            logger.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 是否是可以注册的bean
     * 
     * @param annotations
     * @return
     */
    @Override
    public boolean isRegister(Annotation[] annotations) {
        if (null == annotations || annotations.length == 0) {
            return false;
        }
        for (Annotation annotation : annotations) {
            if ((annotation instanceof Service) || 
                (annotation instanceof Component) || 
                (annotation instanceof Path)) {
                return true;
            }
        }
        return false;
    }
}
