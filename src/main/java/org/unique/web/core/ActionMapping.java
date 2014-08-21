package org.unique.web.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.unique.common.tools.CollectionUtil;
import org.unique.web.annotation.Action;
import org.unique.web.annotation.Action.HttpMethod;
import org.unique.web.annotation.Path;
import org.unique.web.interceptor.Interceptor;
import org.unique.web.interceptor.InterceptorFactory;
import org.unique.web.interceptor.impl.InterceptorFactoryBuilder;
import org.unique.web.route.Route;
import org.unique.web.route.RouteMatcher;

/**
 * 动作映射器
 * 
 * @author rex
 */
public class ActionMapping {

    private static Logger logger = Logger.getLogger(ActionMapping.class);

    // 保存扫描到的所有路由配置
    private Map<RouteMatcher, Route> urlMapping = CollectionUtil.newHashMap();

    // private static Logger log = Logger.getLogger(ActionMapping.class);
    private static final String SLASH = "/";

    private Interceptor[] interceptors;

    private ActionMapping() {
    }

    public static ActionMapping single() {
        return SingleHoder.single;
    }

    private static class SingleHoder {

        private static final ActionMapping single = new ActionMapping();
    }

    /**
     * 自定义公共方法
     * 
     * @author：rex
     * @return
     */
    private static Set<String> buildExcludedMethodName() {
        Set<String> excludedMethodName = new HashSet<String>();
        Method[] methods = Controller.class.getMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 0) excludedMethodName.add(m.getName());
        }
        return excludedMethodName;
    }

    /**
     * 构建路由map
     * 
     * @author：rex
     * @param pack
     * @return
     */
    public void buildActionMapping() {
        urlMapping.clear();

        // 要过滤的方法
        Set<String> excludedMethodName = buildExcludedMethodName();
        // 所有控制器
        Set<Entry<String, Class<? extends Controller>>> entrySet = Unique.single().controllerMap.entrySet();

        InterceptorFactory factory = InterceptorFactoryBuilder.buildInterceptorFactory();
        this.interceptors = factory.getDefaultInterceptors();
        factory.addToInterceptorsMap(interceptors);

        // 遍历controller
        for (Entry<String, Class<? extends Controller>> entry : entrySet) {
            // controller
            Class<? extends Controller> controller = entry.getValue();

            Interceptor[] controllerInters = factory.getControllerInterceptors(controller);

            Method[] methods = controller.getMethods();
            String path = "/";

            if (controller.isAnnotationPresent(Path.class)) {
                Path annotation = controller.getAnnotation(Path.class);
                path = annotation.value();
            }
            // 遍历method
            for (Method method : methods) {
            	// 非public不作为action处理
            	if(method.getModifiers() != Modifier.PUBLIC){
            		continue;
            	}
                String methodName = method.getName();
                // 过滤controller顶层方法
                if (!excludedMethodName.contains(methodName) && method.getParameterTypes().length == 0) {
                    Interceptor[] methodInters = factory.getMethodInterceptors(method);
                    Interceptor[] actionInters = factory.getActionInterceptors(interceptors, controllerInters, controller, methodInters, method);
                    
                    // 获取路由
                    Action ak = method.getAnnotation(Action.class);
                    RouteMatcher mac = null;
                    if (null != ak) {
                        String action = ak.value().trim();
                        HttpMethod methodType = (null == ak.method()) ? HttpMethod.ALL : ak.method();
                        if ("".equals(action)) {
                            throw new IllegalArgumentException(controller.getName() + "." + methodName
                                    + "(): The argument of ActionKey can not be blank.");
                        }
                        if (urlMapping.containsKey(action)) {
                            warnning(action, controller, method);
                            continue;
                        }

                        if (!action.startsWith("/")) {
                            action = path.equals("/") ? path + action : path + "/" + action;
                        }
                        Route route = new Route(actionInters, controller, path, action, method, methodType, path);

                        mac = new RouteMatcher(action);
                        urlMapping.put(mac, route);
                    } else if (methodName.equals("index")) {
                        String action = path.equals(SLASH) ? SLASH + methodName : path + SLASH + methodName;
                        Route route = new Route(actionInters, controller, path, action, method, HttpMethod.ALL, path);
                        mac = new RouteMatcher(action);
                        route = urlMapping.put(mac, route);
                        if (null != route) {
                            warnning(route.getAction(), route.getControllerClass(), route.getMethod());
                        }
                    } else {
                        String action = path.equals(SLASH) ? SLASH + methodName : path + SLASH + methodName;
                        if (urlMapping.containsKey(action)) {
                            warnning(action, controller, method);
                            continue;
                        }
                        Route route = new Route(actionInters, controller, path, action, method, HttpMethod.ALL, path);
                        mac = new RouteMatcher(action);
                        urlMapping.put(mac, route);
                    }
                }
            }
        }
    }

    private static final void warnning(String actionKey, Class<? extends Controller> controllerClass, Method method) {
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------------------------------------\nWarnning!!!\n").append("ActionKey already used: \"").append(actionKey).append("\" \n").append("Action can not be mapped: \"").append(controllerClass.getName()).append(".").append(method.getName()).append("()\" \n").append("--------------------------------------------------------------------------------");
        logger.warn(sb.toString());
    }

    public Route getRoute(String url) {
        Route route = urlMapping.get(url);
        if (route != null) {
            return route;
        } else {
            if (url.equals("") || url.equals("/") || url.equals("/index/") ) {
                url = "/index";
            }
            // 解析url中所有参数
            for (RouteMatcher matcher : this.urlMapping.keySet()) {
                String[] params = matcher.getUrlParameters(url);
                if (params != null) {
                    route = this.urlMapping.get(matcher);
                    route.setParams(params);
                    break;
                }
            }
        }
        return route;
    }
}
