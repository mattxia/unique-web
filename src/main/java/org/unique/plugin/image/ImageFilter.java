package org.unique.plugin.image;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.common.tools.FileUtil;
import org.unique.plugin.image.handler.ImageHandler;

/**
 * 图片处理核心过滤器
 * @author rex
 *
 */
public class ImageFilter implements Filter {
    
    private int contextPathLength;
    
    private ImageHandler imgHandler = ImageHandler.single();

    /**
     * init
     */
    public void init(FilterConfig config) throws ServletException {
        String contextPath = config.getServletContext().getContextPath();
        contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0 : contextPath.length());
    }

    /**
     * dofilter
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String target = request.getRequestURI().replaceFirst(request.getContextPath(), "");
        if (contextPathLength != 0) {
            target = request.getRequestURI().substring(contextPathLength);
        }
        String fileName = target.substring(target.lastIndexOf("/") + 1);
        if (isImg(FileUtil.getSuffix(fileName)) && null != request.getQueryString()) {
            response = imgHandler.handler(target, request, response);
        }
        chain.doFilter(request, response);
    }

    /**
     * 是否是图片类型
     * @param fileName
     * @return
     */
    private boolean isImg(String fileName) {
        Pattern p = Pattern.compile("^(jpeg|jpg|png|gif|bmp|webp)([\\w-./?%&=]*)?$");
        Matcher m = p.matcher(fileName);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }
    
}
