package org.unique.web.render.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.web.core.ActionContext;
import org.unique.web.render.Render;

/**
 * RedirectRender with status: 302 Found.
 */
public class RedirectRender implements Render {
	
	private String url;
	private boolean withQueryString;
	private static final String contextPath = getContxtPath();
	
	static String getContxtPath() {
		String cp = ActionContext.single().getContextPath();
		return ("".equals(cp) || "/".equals(cp)) ? null : cp;
	}
	
	public RedirectRender(String url) {
		this.url = url;
		this.withQueryString = false;
	}
	
	public RedirectRender(String url, boolean withQueryString) {
		this.url = url;
		this.withQueryString =  withQueryString;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		if (contextPath != null && url.indexOf("://") == -1)
			url = contextPath + url;
		
		if (withQueryString) {
			String queryString = request.getQueryString();
			if (queryString != null)
				if (url.indexOf("?") == -1)
					url = url + "?" + queryString;
				else
					url = url + "&" + queryString;
		}
		
		try {
			response.sendRedirect(url);	// always 302
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

