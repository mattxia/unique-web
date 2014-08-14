package org.unique.web.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.unique.web.interceptor.Interceptor;
import org.unique.web.route.Route;

/**
 * action执行器
 * 
 * @author：rex
 * @create_time：2014-6-24 上午11:40:27
 * @version：V1.0
 */
public class ActionInvocation {

    private Controller controller;

    private Route action;

    private Interceptor[] inters;

    private int index = 0;

    private static final Object[] NULL_ARGS = new Object[0];

    protected ActionInvocation() {
    }

    public ActionInvocation(Route action, Controller controller) {
        this.controller = controller;
        this.action = action;
        this.inters = action.getInterceptors();
    }

    public void invoke() {
        if (index < inters.length) {
            inters[index++].intercept(this);
        } else if (index++ == inters.length) {
            try {
                action.getMethod().invoke(controller, NULL_ARGS);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getTargetException();
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException) cause;
                }
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
