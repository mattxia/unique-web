package org.unique.web.interceptor.impl;

import org.unique.common.tools.StringUtils;
import org.unique.web.core.Const;
import org.unique.web.interceptor.GlobalInterceptor;
import org.unique.web.interceptor.InterceptorFactory;

public class InterceptorFactoryBuilder {

    public static InterceptorFactory buildInterceptorFactory() {
        String globalInterc = Const.GLOBAL_INTERCEPTOR;
        try {
            if (StringUtils.isNotBlank(globalInterc)) {
                Object clazz = Class.forName(globalInterc).newInstance();
                GlobalInterceptor g = (GlobalInterceptor) clazz;
                return new InterceptorFactoryImpl(g);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("global interceptor class not found !");
        } catch (InstantiationException e) {
            throw new RuntimeException("error global interceptor configuration !");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("error global interceptor configuration !");
        }
        return new InterceptorFactoryImpl(null);
    }

}
