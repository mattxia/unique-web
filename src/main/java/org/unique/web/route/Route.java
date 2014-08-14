package org.unique.web.route;

import java.lang.reflect.Method;

import org.unique.web.annotation.Action.HttpMethod;
import org.unique.web.core.Controller;
import org.unique.web.interceptor.Interceptor;

/**
 * 动作对象
 * @author：rex
 */
public class Route {

	private final Class<? extends Controller> controllerClass;
	private final String path;
	private final String action;
	private final Method method;
	private String[] params;
	private final HttpMethod methodType;
	private final String viewPath;
	private final Interceptor interceptors[];
	
	public Route(Interceptor[] interceptors,Class<? extends Controller> controllerClass, String path,
			String action, Method method, HttpMethod methodType, String viewPath) {
		this.controllerClass = controllerClass;
		this.path = path;
		this.action = action;
		this.method = method;
		this.methodType = methodType;
		this.viewPath = viewPath;
		this.interceptors = interceptors;
	}
	
    public Class<? extends Controller> getControllerClass() {
		return controllerClass;
	}
	public String getPath() {
		return path;
	}
	public String getAction() {
		return action;
	}
	public Method getMethod() {
		return method;
	}
	
	public String getViewPath() {
		return viewPath;
	}

	public HttpMethod getMethodType() {
		return methodType;
	}

	public Interceptor[] getInterceptors() {
		return interceptors;
	}

    
    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String getMethodName() {
        return this.method.getName();
    }

}
