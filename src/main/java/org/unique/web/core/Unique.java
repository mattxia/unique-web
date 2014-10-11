package org.unique.web.core;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.unique.common.tools.ClassHelper;
import org.unique.common.tools.CollectionUtil;
import org.unique.ioc.Container;
import org.unique.ioc.impl.BeanFactory;
import org.unique.ioc.impl.DefaultContainerImpl;
import org.unique.web.annotation.Path;
import org.unique.web.handler.Handler;
import org.unique.web.handler.impl.DefalutHandlerImpl;
import org.unique.web.interceptor.InterceptorFactory;
import org.unique.web.route.Route;

/**
 * unique
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public class Unique {

    private Logger logger = Logger.getLogger(Unique.class);

    private String[] packages;

    private DefalutHandlerImpl handler;

    private ActionMapping actionMapping = ActionMapping.single();

    // ioc container
    private Container container;

    // controller map
    public final Map<String, Class<? extends Controller>> controllerMap = CollectionUtil.newConcurrentHashMap();

    private Unique() {
    }

    public static Unique single() {
        return SingleHoder.single;
    }

    private static class SingleHoder {

        private static final Unique single = new Unique();
    }

    boolean init() {

        // init const config
        Const.init();

        // init container
        initIOC();

        // init actionMapping
        initActionMapping();
        
        // init global interceptor
        initInterceptor();
        
        // init handler
        initHandler();
        
        return true;
    }

    /**
     * 初始化全局拦截器
     */
    private void initInterceptor() {
    	try {
			InterceptorFactory.buildInterceptor();
		} catch (InstantiationException e) {
			logger.error("init interceptor error : " + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("init interceptor error : " + e.getMessage());
		}
	}

	private void initIOC() {
        container = DefaultContainerImpl.single();
        BeanFactory.init(container);
        loadClasses();
        logger.info("beans : " + container.getBeanMap());
    }

    private void initHandler() {
        handler = new DefalutHandlerImpl();
    }

    private void initActionMapping() {
    	Map<String, Route> mapping = actionMapping.buildActionMapping();
    	Set<String> matcherSet = mapping.keySet();
    	for(String r : matcherSet){
    		logger.info("action ：" + r);
    	}
    	logger.info("action size ：" + matcherSet.size());
    }

    public Handler getHandler() {
        return this.handler;
    }

    private void loadClasses() {
        String scanPackage = Const.BASE_PACKAGE;
        if ((scanPackage == null) || (scanPackage.length() == 0)) {
            throw new IllegalArgumentException("the scan package not null !");
        }
        if (scanPackage.indexOf(',') == -1) {
            this.packages = new String[] { scanPackage };
        } else {
            this.packages = scanPackage.split(",");
        }
        for (String pack : packages) {
            scanPack(pack);
        }
    }

    @SuppressWarnings({ "unchecked" })
    private void scanPack(String pack) {
        if (pack.endsWith(".*")) {
            pack = pack.substring(0, pack.length() - 2);
        }
        Set<Class<?>> classes = ClassHelper.scanPackage(pack);
        for (Class<?> clazz : classes) {
            // determine whether the controller
        	
        	if(Controller.class.isAssignableFrom(clazz)){
        		Path path = clazz.getAnnotation(Path.class);
        		if(null != path){
        			controllerMap.put(path.value(), (Class<? extends Controller>) clazz);
        		}
        	}
            if (container.isRegister(clazz.getAnnotations())) {
                // scan the class to container
                container.registBean(clazz);
            }
        }
        // init wired
        container.initWired();
    }

}
