package org.unique.web.render.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jetbrick.template.JetContext;
import jetbrick.template.JetTemplate;
import jetbrick.template.utils.ExceptionUtils;
import jetbrick.template.web.JetWebContext;
import jetbrick.template.web.JetWebEngineLoader;

import org.unique.web.core.Const;
import org.unique.web.render.Render;

/**
 * JetRender.
 */
public class JetRender implements Render {
	

	private String view;
	
	public JetRender() {
	}
	
	public JetRender(String view) {
		this.view = view;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		if (JetWebEngineLoader.unavailable()) {
			JetWebEngineLoader.setServletContext(request.getSession().getServletContext());
		}
		JetContext context = new JetWebContext(request, response);
		if(!view.endsWith(Const.VIEW_EXT)){
			view += Const.VIEW_EXT;
		}
		if(view.endsWith("/" + Const.VIEW_EXT) ){
			return;
		}
		JetTemplate template = JetWebEngineLoader.getJetEngine().getTemplate(view);
		try {
			template.render(context, response.getOutputStream());
		} catch (IOException e) {
			throw ExceptionUtils.uncheck(e);
		}
	}
	
}