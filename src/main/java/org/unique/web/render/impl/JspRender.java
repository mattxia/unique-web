package org.unique.web.render.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.web.core.Const;
import org.unique.web.render.Render;

/**
 * JspRender.
 */
public class JspRender implements Render {
	

	private String view;
	
	public JspRender() {
		// TODO Auto-generated constructor stub
	}
	
	public JspRender(String view) {
		this.view = view;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		try {
			if(!view.endsWith(Const.VIEW_EXT)){
				view += Const.VIEW_EXT;
			}
			if(view.endsWith("/" + Const.VIEW_EXT) ){
				return;
			}
			request.getRequestDispatcher(view).forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}