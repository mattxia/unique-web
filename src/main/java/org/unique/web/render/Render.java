package org.unique.web.render;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 模板渲染接口
 * @author rex
 *
 */
public interface Render {

    /**
     * 渲染
     * @param request
     * @param response
     * @param viewPath
     */
	void render(HttpServletRequest request, HttpServletResponse response, String viewPath);
	
}
