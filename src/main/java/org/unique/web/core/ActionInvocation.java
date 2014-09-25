package org.unique.web.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.unique.web.interceptor.AbstractInterceptor;
import org.unique.web.interceptor.Interceptor;
import org.unique.web.interceptor.InterceptorFactory;
import org.unique.web.route.Route;

/**
 * actionInvocation
 * @author:rex
 * @date:2014年9月25日
 * @version:1.0
 */
public class ActionInvocation {

	private Logger logger = Logger.getLogger(ActionInvocation.class);
	
    private Controller controller;

    private Route action;

    private static final Object[] NULL_ARGS = new Object[0];
    
    private List<Interceptor> interceptorList;
    
    private Integer pos = 0;

    protected ActionInvocation() {
    }

    public ActionInvocation(Route action, Controller controller) {
        this.controller = controller;
        this.action = action;
        this.interceptorList = InterceptorFactory.getInterceptors();
    }

    public void invoke() throws Exception {
    	try {
    		if(interceptorList.size() == 0 || interceptorList.size() == pos){
    			action.getMethod().invoke(controller, NULL_ARGS);
    		} else{
    			Interceptor inter = interceptorList.get(pos);
    			pos++;
    			if(inter instanceof AbstractInterceptor){
    				AbstractInterceptor ab = (AbstractInterceptor)inter;
    				ab.before(this);
    				ab.intercept(this);
    				ab.after(this);
    			} else{
    				inter.intercept(this);
    			}
    		}
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException - action invocation execute error :" + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException - action invocation execute error :" + e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error("InvocationTargetException - action invocation execute error :" + e.getMessage());
		}
    }

    public Controller getController() {
        return controller;
    }

    public String getAction() {
        return action.getAction();
    }

    public String getPath() {
        return action.getPath();
    }

    public Method getMethod() {
        return action.getMethod();
    }

    public String getMethodName() {
        return action.getMethodName();
    }

    public String getViewPath() {
        return action.getViewPath();
    }

}
