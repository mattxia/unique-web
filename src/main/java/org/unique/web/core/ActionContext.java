package org.unique.web.core;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unique.util.WebUtil;

/**
 * 用threadLocal存储上下文对象
 * @author rex
 *
 */
public final class ActionContext {

    private static final ThreadLocal<ActionContext> actionContextThreadLocal = new ThreadLocal<ActionContext>();
    
    private ServletContext context;
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    /**
     * get ServletContext
     */
    public ServletContext getServletContext() {
        return context;
    }

    /**
     * get Request
     */
    public HttpServletRequest getHttpServletRequest() {
        return request;
    }

    /**
     * get Response
     */
    public HttpServletResponse getHttpServletResponse() {
        return response;
    }

    /**
     * get HttpSession
     */
    public HttpSession getHttpSession() {
        return request.getSession();
    }

    /**
     * get ActionContext
     */
    public static ActionContext single() {
        ActionContext actionContext = actionContextThreadLocal.get();
        if(null != actionContext){
            return actionContext;
        }
        actionContextThreadLocal.set(new ActionContext());
        return actionContextThreadLocal.get();
    }
    
    public String getContextPath(){
        return context.getContextPath();
    }
    
    void setActionContext(ServletContext context){
        WebUtil.setWebRootPath(context.getRealPath("/"));
        single().context = context;
    }
    
    void setActionContext(HttpServletRequest request, HttpServletResponse response) {
        single().request = request;
        single().response = response;
        actionContextThreadLocal.set(single());
    }

    static void removeActionContext() {
        actionContextThreadLocal.remove();
    }
}

