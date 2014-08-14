package org.unique.web.render.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.common.tools.IOUtil;
import org.unique.web.core.Const;
import org.unique.web.render.Render;

/**
 * JavascriptRender.
 */
public class JavascriptRender implements Render {
	
	private static final String contentType = "text/javascript;charset=" + Const.ENCODING;
	private String jsText;
	
	public JavascriptRender(String jsText) {
		this.jsText = jsText;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		PrintWriter writer = null;
		try {
			response.setContentType(contentType);
	        writer = response.getWriter();
	        writer.write(jsText);
	        writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtil.closeQuietly(writer);
		}
	}
}





