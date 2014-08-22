package org.unique.web.render;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * template render interface
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
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
