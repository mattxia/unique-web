package org.unique.web.render.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unique.common.tools.IOUtil;
import org.unique.common.tools.JSONUtil;
import org.unique.web.core.Const;
import org.unique.web.render.Render;

/**
 * JsonRender
 * 
 * @author：rex
 * @create_time：2014-6-27
 * @version：V1.0
 */
public class JsonRender implements Render {

    private String jsonText;

    private String[] attrs;

    public JsonRender() {
    }

    @SuppressWarnings("serial")
    public JsonRender(final String key, final Object value) {
        if (key == null) throw new IllegalArgumentException("The parameter key can not be null.");
        this.jsonText = JSONUtil.map2Json(new HashMap<String, Object>() {

            {
                put(key, value);
            }
        });
    }

    public JsonRender(String[] attrs) {
        if (attrs == null) throw new IllegalArgumentException("The parameter attrs can not be null.");
        this.attrs = attrs;
    }

    public JsonRender(final String jsonText) {
        if (jsonText == null) throw new IllegalArgumentException("The parameter jsonString can not be null.");
        this.jsonText = jsonText;
    }

    public JsonRender(Object object) {
        if (object == null) throw new IllegalArgumentException("The parameter object can not be null.");
        this.jsonText = JSONUtil.toJSON(object);
    }

    public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
        if (jsonText == null) buildJsonText(request);

        PrintWriter writer = null;
        try {
            response.setHeader("Pragma", "no-cache"); // HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            String userAgent = request.getHeader("User-Agent");
            // 处理IE返回json跳出下载框
            if (userAgent.contains("MSIE")) {
                response.setContentType("text/html;charset=" + Const.ENCODING);
            } else {
                response.setContentType("application/json;charset=" + Const.ENCODING);
            }
            writer = response.getWriter();
            writer.write(jsonText);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtil.closeQuietly(writer);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void buildJsonText(HttpServletRequest request) {
        Map map = new HashMap();
        if (attrs != null) {
            for (String key : attrs)
                map.put(key, request.getAttribute(key));
        } else {
            Enumeration<String> attrs = request.getAttributeNames();
            while (attrs.hasMoreElements()) {
                String key = attrs.nextElement();
                Object value = request.getAttribute(key);
                map.put(key, value);
            }
        }
        this.jsonText = JSONUtil.map2Json(map);
    }
}
