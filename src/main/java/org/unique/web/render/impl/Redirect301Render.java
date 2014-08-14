package org.unique.web.render.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.web.render.Render;

/**
 * Redirect301Render.
 */
public class Redirect301Render implements Render {
	
	private String url;
	private boolean withQueryString;
	private static final String contextPath = RedirectRender.getContxtPath();
	
	public Redirect301Render(String url) {
		this.url = url;
		this.withQueryString = false;
	}
	
	public Redirect301Render(String url, boolean withQueryString) {
		this.url = url;
		this.withQueryString = withQueryString;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		if (contextPath != null && url.indexOf("://") == -1)
			url = contextPath + url;
		
		if (withQueryString) {
			String queryString = request.getQueryString();
			if (queryString != null)
				url = url + "?" + queryString;
		}
		
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		// response.sendRedirect(url);	// always 302
		response.setHeader("Location", url);
		response.setHeader("Connection", "close");
	}
}
