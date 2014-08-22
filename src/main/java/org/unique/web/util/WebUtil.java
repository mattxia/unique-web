package org.unique.web.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

/**
 * system path tools
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public final class WebUtil {

    private static String webRootPath;

    private static String rootClassPath;

    private static final String SLASH = "/";

    private WebUtil() {
    }

    public static String getPath(Class<?> clazz) {
        String path = clazz.getResource("").getPath();
        return new File(path).getAbsolutePath();
    }

    public static String getPath(Object object) {
        String path = object.getClass().getResource("").getPath();
        return new File(path).getAbsolutePath();
    }

    public static String getRootClassPath() {
        if (rootClassPath == null) {
            try {
                String path = WebUtil.class.getClassLoader().getResource("").toURI().getPath();
                rootClassPath = new File(path).getAbsolutePath();
            } catch (Exception e) {
                String path = WebUtil.class.getClassLoader().getResource("").getPath();
                rootClassPath = new File(path).getAbsolutePath();
            }
        }
        return rootClassPath;
    }

    public static void setWebRootPath(String webRootPath) {
        if (webRootPath == null) return;
        if (webRootPath.endsWith(File.separator)) webRootPath = webRootPath.substring(0, webRootPath.length() - 1);
        WebUtil.webRootPath = webRootPath;
    }

    public static String getPackagePath(Object object) {
        Package p = object.getClass().getPackage();
        return p != null ? p.getName().replaceAll("\\.", "/") : "";
    }

    public static File getFileFromJar(String file) {
        throw new RuntimeException("Not finish. Do not use this method.");
    }

    public static String getWebRootPath() {
        if (webRootPath == null) webRootPath = detectWebRootPath();
        ;
        return webRootPath;
    }
    
    private static String detectWebRootPath() {
        try {
            String path = WebUtil.class.getResource("/").toURI().getPath();
            return new File(path).getParentFile().getParentFile().getCanonicalPath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 获取相对路径
     * @param request
     * @param filterPath
     * @return
     */
    public static String getRelativePath(HttpServletRequest request, String filterPath) {
        String path = request.getRequestURI();
        String contextPath = request.getContextPath();

        path = path.substring(contextPath.length());

        if (path.length() > 0) {
            path = path.substring(1);
        }

        if (!path.startsWith(filterPath) && filterPath.equals(path + SLASH)) {
            path += SLASH;
        }
        if (path.startsWith(filterPath)) {
            path = path.substring(filterPath.length());
        }

        if (!path.startsWith(SLASH)) {
            path = SLASH + path;
        }

        return path;
    }

}
