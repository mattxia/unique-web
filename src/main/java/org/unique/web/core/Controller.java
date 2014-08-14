package org.unique.web.core;

import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unique.web.render.Render;
import org.unique.web.render.RenderFactory;

/**
 * 风萧萧兮易水寒，coding一去兮不复还！
 * 
 * @author：rex
 * @create_time：2014-6-24 上午11:14:05
 * @version：V1.0
 */
@SuppressWarnings("unchecked")
public abstract class Controller {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    private Set<String> headers = null;

    private String[] urlPara;

    private Render render;

    private static final RenderFactory renderFactory = RenderFactory.single();

    protected Controller() {
    }

    /**
     * 初始化controller
     * 
     * @param request
     * @param response
     * @param urlPara
     */
    public void init(HttpServletRequest request, HttpServletResponse response, String[] urlPara) {
        this.request = request;
        this.response = response;
        this.urlPara = urlPara;
    }

    /**
     * @return path info Example return: "/example/foo"
     */
    public String pathInfo() {
        return request.getPathInfo();
    }

    /**
     * @return the servlet path
     */
    public String servletPath() {
        return request.getServletPath();
    }

    /**
     * @return the context path
     */
    public String contextPath() {
        return request.getContextPath();
    }

    /**
     * 获取请求头信息
     * 
     * @param header
     * @return
     */
    public String headers(String header) {
        return request.getHeader(header);
    }

    /**
     * 获取请求头
     * 
     * @return all headers
     */
    public Set<String> headers() {
        if (headers == null) {
            headers = new TreeSet<String>();
            Enumeration<String> enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                headers.add(enumeration.nextElement());
            }
        }
        return headers;
    }

    public String queryString() {
        return request.getQueryString();
    }

    /**
     * 获取请求参数
     * 
     * @param name
     * @return
     */
    public String getPara(String name) {
        return request.getParameter(name);
    }

    /**
     * 获取请求参数，没有则设置默认值defaultValue
     * 
     * @param name
     * @param defaultValue
     * @return
     */
    public String getPara(String name, String defaultValue) {
        String result = request.getParameter(name);
        return result != null && !"".equals(result) ? result : defaultValue;
    }

    public Map<String, String[]> getParaMap() {
        return request.getParameterMap();
    }

    public Enumeration<String> getParaNames() {
        return request.getParameterNames();
    }

    public String[] getParaValues(String name) {
        return request.getParameterValues(name);
    }

    public Integer[] getParaValuesToInt(String name) {
        String[] values = request.getParameterValues(name);
        if (values == null) return null;
        Integer[] result = new Integer[values.length];
        for (int i = 0; i < result.length; i++)
            result[i] = Integer.parseInt(values[i]);
        return result;
    }

    public Enumeration<String> getAttrNames() {
        return request.getAttributeNames();
    }

    public void setAttr(String attribute, Object value) {
        request.setAttribute(attribute, value);
    }

    public <T> T getAttr(String name) {
        Object attr = request.getAttribute(name);
        if (null == attr) {
            return null;
        }
        return (T) request.getAttribute(name);
    }

    public String getAttrForStr(String name) {
        return (String) request.getAttribute(name);
    }

    public Integer getAttrForInt(String name) {
        return (Integer) request.getAttribute(name);
    }

    private Integer toInt(String value, Integer defaultValue) {
        if (value == null || "".equals(value.trim())) return defaultValue;
        if (value.startsWith("N") || value.startsWith("n")) return -Integer.parseInt(value.substring(1));
        return Integer.parseInt(value);
    }

    public Integer getParaToInt(String name) {
        return toInt(request.getParameter(name), null);
    }

    public Integer getParaToInt(String name, Integer defaultValue) {
        return toInt(request.getParameter(name), defaultValue);
    }

    private Long toLong(String value, Long defaultValue) {
        if (value == null || "".equals(value.trim())) return defaultValue;
        if (value.startsWith("N") || value.startsWith("n")) return -Long.parseLong(value.substring(1));
        return Long.parseLong(value);
    }

    public Long getParaToLong(String name) {
        return toLong(request.getParameter(name), null);
    }

    public Long getParaToLong(String name, Long defaultValue) {
        return toLong(request.getParameter(name), defaultValue);
    }

    private Boolean toBoolean(String value, Boolean defaultValue) {
        if (value == null || "".equals(value.trim())) return defaultValue;
        value = value.trim().toLowerCase();
        if ("1".equals(value) || "true".equals(value)) {
            return Boolean.TRUE;
        } else if ("0".equals(value) || "false".equals(value)) {
            return Boolean.FALSE;
        }
        throw new RuntimeException("Can not parse the parameter \"" + value + "\" to boolean value.");
    }

    public Boolean getParaToBoolean(String name) {
        return toBoolean(request.getParameter(name), null);
    }

    public Boolean getParaToBoolean(String name, Boolean defaultValue) {
        return toBoolean(request.getParameter(name), defaultValue);
    }

    public Boolean getParaToBoolean() {
        return toBoolean(getPara(), null);
    }

    public Boolean getParaToBoolean(int index) {
        return toBoolean(getPara(index), null);
    }

    public Boolean getParaToBoolean(int index, Boolean defaultValue) {
        return toBoolean(getPara(index), defaultValue);
    }

    private Date toDate(String value, Date defaultValue) {
        if (value == null || "".equals(value.trim())) return defaultValue;
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Date getParaToDate(String name) {
        return toDate(request.getParameter(name), null);
    }

    public Date getParaToDate(String name, Date defaultValue) {
        return toDate(request.getParameter(name), defaultValue);
    }

    public Date getParaToDate() {
        return toDate(getPara(), null);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpSession getSession() {
        return request.getSession();
    }

    public HttpSession getSession(boolean create) {
        return request.getSession(create);
    }

    public <T> T getSessionAttr(String key) {
        HttpSession session = request.getSession(false);
        return session != null ? (T) session.getAttribute(key) : null;
    }

    public Controller setSessionAttr(String key, Object value) {
        request.getSession().setAttribute(key, value);
        return this;
    }

    public Controller removeSessionAttr(String key) {
        HttpSession session = request.getSession(false);
        if (session != null) session.removeAttribute(key);
        return this;
    }

    public String getCookie(String name, String defaultValue) {
        Cookie cookie = getCookieObject(name);
        return cookie != null ? cookie.getValue() : defaultValue;
    }

    public String getCookie(String name) {
        return getCookie(name, null);
    }

    public Integer getCookieToInt(String name) {
        String result = getCookie(name);
        return result != null ? Integer.parseInt(result) : null;
    }

    public Integer getCookieToInt(String name, Integer defaultValue) {
        String result = getCookie(name);
        return result != null ? Integer.parseInt(result) : defaultValue;
    }

    public Long getCookieToLong(String name) {
        String result = getCookie(name);
        return result != null ? Long.parseLong(result) : null;
    }

    public Long getCookieToLong(String name, Long defaultValue) {
        String result = getCookie(name);
        return result != null ? Long.parseLong(result) : defaultValue;
    }

    public Cookie getCookieObject(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) for (Cookie cookie : cookies)
            if (cookie.getName().equals(name)) return cookie;
        return null;
    }

    public Cookie[] getCookieObjects() {
        Cookie[] result = request.getCookies();
        return result != null ? result : new Cookie[0];
    }

    public Controller setCookie(Cookie cookie) {
        response.addCookie(cookie);
        return this;
    }

    public Controller setCookie(String name, String value, int maxAgeInSeconds, String path) {
        setCookie(name, value, maxAgeInSeconds, path, null);
        return this;
    }

    public Controller setCookie(String name, String value, int maxAgeInSeconds, String path, String domain) {
        Cookie cookie = new Cookie(name, value);
        if (domain != null) cookie.setDomain(domain);
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setPath(path);
        response.addCookie(cookie);
        return this;
    }

    public Controller setCookie(String name, String value, int maxAgeInSeconds) {
        setCookie(name, value, maxAgeInSeconds, "/", null);
        return this;
    }

    public Controller removeCookie(String name) {
        setCookie(name, null, 0, "/", null);
        return this;
    }

    public Controller removeCookie(String name, String path) {
        setCookie(name, null, 0, path, null);
        return this;
    }

    /**
     * 移除cookie
     */
    public Controller removeCookie(String name, String path, String domain) {
        setCookie(name, null, 0, path, domain);
        return this;
    }

    /**
     * 获取url上的参数
     */
    public String getPara() {
        if ("".equals(urlPara)) { // urlPara maybe is "" see
            urlPara = null;
        }
        return urlPara[0];
    }

    public String[] getUrlPara() {
        return urlPara;
    }

    /**
     * 根据索引获取url的参数
     */
    public String getPara(int index) {
        if (index < 0) return getPara();
        return urlPara[index];
    }

    /**
     * 根据索引获取url参数，没有给默认值
     */
    public String getPara(int index, String defaultValue) {
        String result = getPara(index);
        return result != null && !"".equals(result) ? result : defaultValue;
    }

    /**
     * 获取url第一个参数并转换为Integer
     * 
     * @param index
     * @return
     */
    public Integer getParaToInt(int index) {
        return toInt(getPara(index), null);
    }

    /**
     * 获取url第一个参数并转换为Integer，没有设置默认值defaultValue
     * 
     * @param index
     * @param defaultValue
     * @return
     */
    public Integer getParaToInt(int index, Integer defaultValue) {
        return toInt(getPara(index), defaultValue);
    }

    /**
     * 获取url第一个参数并转换为Long
     * 
     * @param index
     * @return
     */
    public Long getParaToLong(int index) {
        return toLong(getPara(index), null);
    }

    /**
     * 获取url第一个参数并转换为Long，没有设置默认值defaultValue
     * 
     * @param index
     * @param defaultValue
     * @return
     */
    public Long getParaToLong(int index, Long defaultValue) {
        return toLong(getPara(index), defaultValue);
    }

    /**
     * 获取url第一个参数并转换为Integer
     * 
     * @return
     */
    public Integer getParaToInt() {
        return toInt(getPara(), null);
    }

    /**
     * 获取url第一个参数并转换为Long
     * 
     * @return
     */
    public Long getParaToLong() {
        return toLong(getPara(), null);
    }

    public void render(final String view) {
        this.render = renderFactory.getRender(view);
    }

    public void renderJson(final String jsonText) {
        this.render = renderFactory.getJsonRender(jsonText);
    }

    public void renderJson(final Object object) {
        this.render = renderFactory.getJsonRender(object);
    }

    public void renderJson(final String[] attrs) {
        this.render = renderFactory.getJsonRender(attrs);
    }

    public void renderJson(final String key, final Object value) {
        this.render = renderFactory.getJsonRender(key, value);
    }

    public void renderText(final String text) {
        this.render = renderFactory.getTextRender(text);
    }

    public void renderText(final String text, final String contentType) {
        this.render = renderFactory.getTextRender(text, contentType);
    }

    public void renderJS(final String jsText) {
        this.render = renderFactory.getJavascriptRender(jsText);
    }

    public void renderHtml(final String htmlText) {
        this.render = renderFactory.getHtmlRender(htmlText);
    }
    
    public void redirect(final String url){
        this.render = renderFactory.getRedirectRender(url);
    }

    public Render getRender() {
        return render;
    }
}
