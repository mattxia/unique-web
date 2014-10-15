package org.unique.web.core;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.unique.common.tools.CollectionUtil;
import org.unique.web.annotation.Action;
import org.unique.web.annotation.Action.HttpMethod;
import org.unique.web.annotation.Path;
import org.unique.web.route.Route;

/**
 * actionMapping
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public class ActionMapping {

    private static Logger logger = Logger.getLogger(ActionMapping.class);

    // route mapping
    private Map<String, Route> urlMapping = CollectionUtil.newHashMap();

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
    public Map<String, Route> buildActionMapping() {
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
                String methodName = method.getName();
                // filter the top controller method
                if (!excludedMethodName.contains(methodName) && method.getParameterTypes().length == 0 && method.getModifiers() == Modifier.PUBLIC) {
                    // get action
                    Action ak = method.getAnnotation(Action.class);
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
                        urlMapping.put(action, route);
                    } else if (methodName.equals("index")) {
                        String action = path.equals(SLASH) ? SLASH + methodName : path + SLASH + methodName;
                        Route route = new Route(controller, path, action, method, HttpMethod.ALL, path);
                        route = urlMapping.put(action, route);
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
                        urlMapping.put(action, route);
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
    	if(url.endsWith("/") && url.length() > 1){
    		url = url.substring(0, url.length() - 1);
        }
        Route route = urlMapping.get(url);
        if (route != null) {
            return route;
        } else {
            if (url.equals("") || url.equals("/") || url.equals("/index/") ) {
                url = "/index";
            }
            route = urlMapping.get(url);
            // 取参数
            if(null == route){
        		Pattern p = Pattern.compile("[/\\w]?[\\d]+");
        		Matcher m = p.matcher(url);
        		List<String> paramList = CollectionUtil.newArrayList();
        		int pos = 1;
        		while(m.find()){
        			String mg = m.group().replaceAll("/", "");
        			url = url.replaceFirst(mg, "{"+pos+"}");
        			paramList.add(mg);
        			pos++;
        		}
            	route = urlMapping.get(url);
            	if(null != route){
            		String[] params = new String[paramList.size()];
            		route.setParams(paramList.toArray(params));
                }
            }
            // 取index
            if(null == route){
            	route = urlMapping.get(url + "/index");
            }
        }
        return route;
    }
}
