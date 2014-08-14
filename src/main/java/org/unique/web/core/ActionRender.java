package org.unique.web.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.web.render.Render;

/**
 * ActionRender
 */
public final class ActionRender implements Render {
	
	private String actionUrl;
	
	public ActionRender(String actionUrl) {
		this.actionUrl = actionUrl.trim();
	}
	
	public String getActionUrl() {
		return actionUrl;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		
	}
}
