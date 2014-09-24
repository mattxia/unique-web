package org.unique.web.render.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.common.tools.IOUtil;
import org.unique.web.core.Const;
import org.unique.web.render.Render;
import org.unique.web.render.RenderFactory;

/**
 * ErrorRender.
 */
public class ErrorRender implements Render {
	
	protected static final String contentType = "text/html;charset=" + Const.ENCODING;
	
	protected static final String html404 = "<html><head><title>404 Not Found</title></head><body bgcolor='white'><center><h1>404 Not Found</h1></center><hr><center><a>Unique " + Const.UNIQUE_VERSION + "</a></center></body></html>";
	protected static final String html500 = "<html><head><title>500 Internal Server Error</title></head><body bgcolor='white'><center><h1>500 Internal Server Error</h1></center><hr><center><a>Unique " + Const.UNIQUE_VERSION + "</a></center></body></html>";
	
	protected static final String html401 = "<html><head><title>401 Unauthorized</title></head><body bgcolor='white'><center><h1>401 Unauthorized</h1></center><hr><center><a>Unique " + Const.UNIQUE_VERSION + "</a></center></body></html>";
	protected static final String html403 = "<html><head><title>403 Forbidden</title></head><body bgcolor='white'><center><h1>403 Forbidden</h1></center><hr><center><a>Unique " + Const.UNIQUE_VERSION + "</a></center></body></html>";
	
	protected int errorCode;
	private String view;
	
	public ErrorRender(int errorCode, String view) {
		this.errorCode = errorCode;
		this.view = view;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		response.setStatus(getErrorCode());
		
		// render with view
		if (view != null) {
			RenderFactory.single().getRender(view).render(request, response, null);
			return;
		}
		
		// render with html content
		PrintWriter writer = null;
		try {
			response.setContentType(contentType);
	        writer = response.getWriter();
	        writer.write(getErrorHtml());
	        writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtil.closeQuietly(writer);
		}
	}
	
	public String getErrorHtml() {
		int errorCode = getErrorCode();
		if (errorCode == 404)
			return html404;
		if (errorCode == 500)
			return html500;
		if (errorCode == 401)
			return html401;
		if (errorCode == 403)
			return html403;
		return "<html><head><title>" + errorCode + " Error</title></head><body bgcolor='white'><center><h1>" + errorCode + " Error</h1></center><hr><center><a href='http://www.unique.com'>Unique " + Const.UNIQUE_VERSION + "</a></center></body></html>";
	}
	
	public int getErrorCode() {
		return errorCode;
	}
}





