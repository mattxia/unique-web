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
 * @author：rex
 * @create_time：2014-6-24 下午1:23:55
 * @version：V1.0
 */
public class Unique {

    private Logger logger = Logger.getLogger(Unique.class);

    private String[] packages;

    private DefalutHandlerImpl handler;

    private ActionMapping actionMapping = ActionMapping.single();

    // IOC容器
    private Container container;

    // 存放控制器
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

        // 初始化常量参数
        Const.init();

        // 初始化IOC容器
        initIOC();

        logger.debug("beans : " + container.getBeanMap());

        // 初始化actionMapping
        initActionMapping();

        // 初始化处理器
        initHandler();
    }

    private void initIOC() {
        container = DefaultContainerImpl.single();
        BeanFactory.init(container);
        loadClasses();
    }

    // 初始化handler
    private void initHandler() {
        handler = new DefalutHandlerImpl();
    }

    // 初始化actionMapping
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
            // 控制器
            if (null != clazz.getSuperclass() && clazz.getSuperclass().equals(Controller.class)) {
                Path path = clazz.getAnnotation(Path.class);
                if (null == path) {
                    controllerMap.put("/", (Class<? extends Controller>) clazz);
                } else {
                    controllerMap.put(path.value(), (Class<? extends Controller>) clazz);
                }
            }

            if (container.isRegister(clazz.getAnnotations())) {
                // 将扫描到的对象保存到容器中
                container.registBean(clazz);
            }
        }
        //注册框架对象
        container.registBean(JedisCache.class);
        container.registBean(JedisCache.class);
        // 初始化注入
        container.initWired();
    }

}
