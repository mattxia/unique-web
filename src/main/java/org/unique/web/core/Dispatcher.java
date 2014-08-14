package org.unique.web.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.unique.util.WebUtil;
import org.unique.web.handler.Handler;

/**
 * mvc核心过滤器
 * 
 * @author：rex
 */
public class Dispatcher implements Filter {

    private Logger logger = Logger.getLogger(Dispatcher.class);

    private static Unique unique = Unique.single();

    private Handler handler;

    private int contextPathLength;

    /**
     * 初始化配置
     * 
     * @author：rex
     */
    public void init(FilterConfig config) {

        // 配置参数
        Const.CONFIG_PATH = config.getInitParameter("configPath");
        ActionContext.single().setActionContext(config.getServletContext());

        // 初始化web
        unique.init();

        handler = unique.getHandler();

        String contextPath = config.getServletContext().getContextPath();
        contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0 : contextPath.length());

        logger.debug("init finish!");
    }

    /**
     * doFilter
     * 
     * @author：rex
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String target = request.getRequestURI().replaceFirst(request.getContextPath(), "");
        target = WebUtil.getRelativePath(request, "");
        if (contextPathLength != 0) {
            target = request.getRequestURI().substring(contextPathLength);
        }
        if(target.endsWith(Const.URL_EXT)){
            target = target.substring(0, target.indexOf(Const.URL_EXT));
        }
        ActionContext.single().setActionContext(request, response);
        if (!handler.handle(target, request, response)) {
            chain.doFilter(request, response);
        }
    }

    /**
     * 销毁
     * 
     * @author：rex
     */
    public void destroy() {
        if (null != unique) {
            unique = null;
        }
        if (null != handler) {
            handler = null;
        }
    }

}
