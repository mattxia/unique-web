package org.unique.web.core;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.unique.common.tools.ClassHelper;
import org.unique.common.tools.CollectionUtil;
import org.unique.ioc.Container;
import org.unique.ioc.impl.BeanFactory;
import org.unique.ioc.impl.DefaultContainerImpl;
import org.unique.plugin.cache.JedisCache;
import org.unique.web.annotation.Path;
import org.unique.web.handler.Handler;
import org.unique.web.handler.impl.DefalutHandlerImpl;

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

    void init() {

        // init const config
        Const.init();

        // init container
        initIOC();

        logger.info("beans : " + container.getBeanMap());

        // init actionMapping
        initActionMapping();

        // init handler
        initHandler();
    }

    private void initIOC() {
        container = DefaultContainerImpl.single();
        BeanFactory.init(container);
        loadClasses();
    }

    private void initHandler() {
        handler = new DefalutHandlerImpl();
    }

    private void initActionMapping() {
        actionMapping.buildActionMapping();
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
            if (null != clazz.getSuperclass() && clazz.getSuperclass().equals(Controller.class)) {
                Path path = clazz.getAnnotation(Path.class);
                if (null == path) {
                    controllerMap.put("/", (Class<? extends Controller>) clazz);
                } else {
                    controllerMap.put(path.value(), (Class<? extends Controller>) clazz);
                }
            }

            if (container.isRegister(clazz.getAnnotations())) {
                // scan the class to container
                container.registBean(clazz);
            }
        }
        //register jediscache class to container
        container.registBean(JedisCache.class);
        // init wired
        container.initWired();
    }

}
