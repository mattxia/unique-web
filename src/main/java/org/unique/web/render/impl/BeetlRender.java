package org.unique.web.render.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.ext.servlet.ServletGroupTemplate;
import org.unique.web.core.Const;
import org.unique.web.render.Render;

/**
 * BeetlRender.
 */
public class BeetlRender implements Render {
	
	private static final String contentType = "text/html;charset=" + Const.ENCODING;
	private String view;
	
	public BeetlRender() {
	}
	
	public BeetlRender(String view) {
		this.view = view;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		if(!view.endsWith(Const.VIEW_EXT)){
			view += Const.VIEW_EXT;
		}
		if(view.endsWith("/" + Const.VIEW_EXT) ){
			return;
		}
		String path = view.startsWith("/") ? viewPath + this.view : viewPath + "/" + this.view;
		response.setContentType(contentType);
		response.setCharacterEncoding(Const.ENCODING);
		ServletGroupTemplate.instance().render(path, request, response);
	}
	
}