package org.unique.web.core;

import java.util.Map;

import org.unique.common.tools.CollectionUtil;
import org.unique.common.tools.PropUtil;
import org.unique.common.tools.StringUtils;
import org.unique.web.render.RenderType;

/**
 * const class
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public abstract class Const {

    public static final String UNIQUE_VERSION = "1.0";
    
    public static final String SESSION_CAPTCH_TOKEN = "session_captch_token";
    
    public static final Map<String, String> CONST_MAP = CollectionUtil.newHashMap();

    public static final Map<Integer, String> ERRPR_VIEWMAPPING = CollectionUtil.newHashMap();

    public static final int DEFAULT_PORT = 8080;

    public static boolean REDIS_IS_OPEN = false;

    public static RenderType RENDER_TYPE = RenderType.JSP;
    
    //default url suffix
    public static String URL_EXT = ".jsp";
    
    //default view suffix
    public static String VIEW_EXT = ".jsp";
    
    //default pool type
    public static String POOL_TYPE = "druid";

    //config path
    public static String CONFIG_PATH = null;

    //default encoding
    public static String ENCODING = "utf-8";

    //global interceptor
    public static String GLOBAL_INTERCEPTOR = null;

    //scan packages
    public static String BASE_PACKAGE = null;

    private Const() {
    }

    public static void init() {
        // load the configuration
        Map<String, String> m = PropUtil.getPropertyMap(CONFIG_PATH);
        if (null != m && !m.isEmpty()) {
            CONST_MAP.putAll(m);
            if (null != m && !m.isEmpty()) {
                if (StringUtils.isNotBlank(m.get("unique.encoding"))) {
                    Const.ENCODING = m.get("unique.encoding").trim().toLowerCase();
                }
                if (StringUtils.isNotBlank(m.get("unique.viewType"))) {
                	// beetl-template
                    if (m.get("unique.viewType").trim().equalsIgnoreCase(RenderType.BEETL.toString())) {
                        Const.RENDER_TYPE = RenderType.BEETL;
                    }
                    // jetbrick-template
                    if (m.get("unique.viewType").trim().equalsIgnoreCase(RenderType.JET.toString())) {
                        Const.RENDER_TYPE = RenderType.JET;
                    }
                    // httl-template
                    if (m.get("unique.viewType").trim().equalsIgnoreCase(RenderType.HTTL.toString())) {
                        Const.RENDER_TYPE = RenderType.HTTL;
                    }
                    if (StringUtils.isNotBlank(m.get("unique.urlext"))) {
                        Const.URL_EXT = m.get("unique.urlext").trim();
                    }
                    if (StringUtils.isNotBlank(m.get("unique.viewext"))) {
                        Const.VIEW_EXT = m.get("unique.viewext").trim();
                    }
                }
                if (StringUtils.isNotBlank(m.get("unique.globalInterceptor"))) {
                    Const.GLOBAL_INTERCEPTOR = m.get("unique.globalInterceptor").trim();
                }
                if (StringUtils.isNotBlank(m.get("unique.beanspackage"))) {
                    Const.BASE_PACKAGE = m.get("unique.beanspackage").trim();
                }
                if (StringUtils.isNotBlank(m.get("redis.pool"))) {
                    Const.REDIS_IS_OPEN = Boolean.valueOf(m.get("redis.pool").trim());
                }
                if (StringUtils.isNotBlank(m.get("unique.db.pool"))) {
                    Const.POOL_TYPE = m.get("unique.db.pool").trim();
                }
                if(StringUtils.isNotBlank(m.get("unique.errorpage.404"))){
                	ERRPR_VIEWMAPPING.put(404, m.get("unique.errorpage.404"));
                }
                if(StringUtils.isNotBlank(m.get("unique.errorpage.500"))){
                	ERRPR_VIEWMAPPING.put(500, m.get("unique.errorpage.500"));
                }
            }
        }
    }

    public boolean getDevMode() {
        return false;
    }

    public static String getErrorView(int errorCode) {
        return ERRPR_VIEWMAPPING.get(errorCode);
    }

}
