package org.unique.web.handler.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.unique.ioc.BeanType;
import org.unique.ioc.impl.BeanFactory;
import org.unique.web.annotation.Action.HttpMethod;
import org.unique.web.core.ActionInvocation;
import org.unique.web.core.ActionMapping;
import org.unique.web.core.Controller;
import org.unique.web.exception.ActionException;
import org.unique.web.handler.Handler;
import org.unique.web.render.Render;
import org.unique.web.render.RenderFactory;
import org.unique.web.route.Route;

/**
 * action处理器
 * 
 * @author：rex
 * @create_time：2014-6-24 上午11:16:12
 * @version：V1.0
 */
public class DefalutHandlerImpl implements Handler {

    private final ActionMapping actionMapping = ActionMapping.single();

    private static final RenderFactory renderFactory = RenderFactory.single();

    private Logger logger = Logger.getLogger(DefalutHandlerImpl.class);

    public DefalutHandlerImpl() {
    }

    public final boolean handle(String target, HttpServletRequest request, HttpServletResponse response) {
        // 不处理静态资源
        if (target.indexOf(".") != -1) {
            return false;
        }

        logger.debug("target:" + target);

        // 获取路由
        Route route = actionMapping.getRoute(target);

        if (route == null) {
            String qs = request.getQueryString();
            logger.warn("404 Action Not Found: " + (qs == null ? target : target + "?" + qs));
            renderFactory.getErrorRender(404).render(request, response, null);
            return true;
        }
        try {
            // 验证是否
            if (!verifyMethod(route.getMethodType(), request.getMethod())) {
                logger.warn("404 Error request method");
                renderFactory.getErrorRender(404).render(request, response, null);
                return true;
            }
            Controller controller = (Controller) BeanFactory.getBean(route.getControllerClass().getName(), BeanType.SINGLE);
            controller.init(request, response, route.getParams());
            logger.debug("reuqestURL：[" + target + "]");

            new ActionInvocation(route, controller).invoke();
            
            Render render = controller.getRender();
            if (render != null) {
                render.render(request, response, route.getViewPath());
            } else {
                render = renderFactory.getDefaultRender(route.getMethodName());
            }
            return true;
        } catch (ActionException e) {
            int errorCode = e.getErrorCode();
            if (errorCode == 404) {
                String qs = request.getQueryString();
                logger.warn("404 Not Found: " + (qs == null ? target : target + "?" + qs));
            } else if (errorCode == 401) {
                String qs = request.getQueryString();
                logger.warn("401 Unauthorized: " + (qs == null ? target : target + "?" + qs));
            } else if (errorCode == 403) {
                String qs = request.getQueryString();
                logger.warn("403 Forbidden: " + (qs == null ? target : target + "?" + qs));
            }
            e.getErrorRender().render(request, response, null);
        } catch (Exception e) {
            logger.warn("Exception: " + e.getMessage());
            renderFactory.getErrorRender(500).render(request, response, null);
        }
        return false;
    }

    /**
     * 验证请求方法
     * 
     * @author：rex
     * @param m
     * @param method
     * @return
     */
    private boolean verifyMethod(HttpMethod methodType, String method) {
        if (methodType == HttpMethod.ALL) {
            return true;
        }
        if (methodType == HttpMethod.GET) {
            return method.trim().equals(HttpMethod.GET.toString());
        }
        if (methodType == HttpMethod.POST) {
            return method.trim().equals(HttpMethod.POST.toString());
        }
        if (methodType == HttpMethod.PUT) {
            return method.trim().equals(HttpMethod.PUT.toString());
        }
        if (methodType == HttpMethod.DELETE) {
            return method.trim().equals(HttpMethod.DELETE.toString());
        }
        return false;
    }

}
