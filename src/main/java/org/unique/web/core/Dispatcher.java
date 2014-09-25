package org.unique.web.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.unique.web.handler.Handler;
import org.unique.web.util.WebUtil;

/**
 * mvc core filter
 */
@WebFilter(urlPatterns = {"/*"}, 
asyncSupported = true,
initParams = {@WebInitParam(name="configPath",value="config.properties")})
public class Dispatcher implements Filter {
	
    private Logger logger = Logger.getLogger(Dispatcher.class);
    
    private static Unique unique = Unique.single();
    
    private static boolean isInit = false;

    private static Handler handler;

    private int contextPathLength;

    /**
     * init
     */
    public void init(FilterConfig config) {
    	
    	if(!isInit){
    		// config path
            Const.CONFIG_PATH = config.getInitParameter("configPath");
            ActionContext.single().setActionContext(config.getServletContext());

            // init web
            isInit = unique.init();

            handler = unique.getHandler();

            String contextPath = config.getServletContext().getContextPath();
            contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0 : contextPath.length());

            logger.info("unique web init finish!");
    	}
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
        
        // set reqest and response
        ActionContext.single().setActionContext(request, response);
        
        if (!handler.handle(target, request, response)) {
            chain.doFilter(request, response);
        }
    }

    /**
     * destroy
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
