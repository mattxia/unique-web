package org.unique.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * handler
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public interface Handler {

	boolean handle(String target, HttpServletRequest request, HttpServletResponse response);
}
