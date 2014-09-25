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
import org.unique.web.route.Route;
import org.unique.web.route.RouteMatcher;

/**
 * actionMapping
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public class ActionMapping {

    private static Logger logger = Logger.getLogger(ActionMapping.class);

    // route mapping
    private Map<RouteMatcher, Route> urlMapping = CollectionUtil.newHashMap();

    // private static Logger log = Logger.getLogger(ActionMapping.class);
    private static final String SLASH = "/";

    private ActionMapping() {
    }

    public static ActionMapping single() {
        return SingleHoder.single;
    }

    private static class SingleHoder {

        private static final ActionMapping single = new ActionMapping();
    }
    
    /**
     * the custom method
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
     * build routing mapping
     */
    public Map<RouteMatcher, Route> buildActionMapping() {
        urlMapping.clear();

        // to filter method
        Set<String> excludedMethodName = buildExcludedMethodName();
        // all controller
        Set<Entry<String, Class<? extends Controller>>> entrySet = Unique.single().controllerMap.entrySet();

        // for controller
        for (Entry<String, Class<? extends Controller>> entry : entrySet) {
            // controller
            Class<? extends Controller> controller = entry.getValue();

            Method[] methods = controller.getMethods();
            String path = "/";

            if (controller.isAnnotationPresent(Path.class)) {
                Path annotation = controller.getAnnotation(Path.class);
                path = annotation.value();
            }
            // for method
            for (Method method : methods) {
            	// non-public not as action to deal with
            	if(method.getModifiers() != Modifier.PUBLIC){
            		continue;
            	}
                String methodName = method.getName();
                // filter the top controller method
                if (!excludedMethodName.contains(methodName) && method.getParameterTypes().length == 0) {
                	
                    // get action
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
                        Route route = new Route(controller, path, action, method, methodType, path);

                        mac = new RouteMatcher(action);
                        urlMapping.put(mac, route);
                    } else if (methodName.equals("index")) {
                        String action = path.equals(SLASH) ? SLASH + methodName : path + SLASH + methodName;
                        Route route = new Route(controller, path, action, method, HttpMethod.ALL, path);
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
                        Route route = new Route(controller, path, action, method, HttpMethod.ALL, path);
                        mac = new RouteMatcher(action);
                        urlMapping.put(mac, route);
                    }
                }
            }
        }
        return urlMapping;
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
            // parse url parameters
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
