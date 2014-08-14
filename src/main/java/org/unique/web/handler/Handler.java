package org.unique.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Handler {

	boolean handle(String target, HttpServletRequest request, HttpServletResponse response);
}
