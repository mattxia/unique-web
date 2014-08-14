package org.unique.web.core;

import java.util.Map;

import org.unique.common.tools.CollectionUtil;
import org.unique.common.tools.PropUtil;
import org.unique.common.tools.StringUtils;
import org.unique.web.render.RenderType;

/**
 * 常量类
 * 
 * @author：rex
 * @create_time：2014-6-25 上午10:04:36
 * @version：V1.0
 */
public abstract class Const {

    /*
     * 版本号
     */
    public static final String UNIQUE_VERSION = "1.0";
    
    /*
     * 验证码session标识
     */
    public static final String SESSION_CAPTCH_TOKEN = "session_captch_token";
    
    /*
     * sql key
     */
    public static final String SQL_FIELD = "sql_";
    
    /*
     * params key
     */
    public static final String PARAM_FIELD = "param_";
    
    /*
     * 常量map
     */
    public static final Map<String, String> CONST_MAP = CollectionUtil.newHashMap();

    /*
     * 错误视图map
     */
    public static final Map<Integer, String> ERRPR_VIEWMAPPING = CollectionUtil.newHashMap();

    /*
     * 服务默认端口
     */
    public static final int DEFAULT_PORT = 8080;

    /*
     * 是否开启redis缓存
     */
    public static boolean REDIS_IS_OPEN = false;

    /*
     * 默认渲染视图类型
     */
    public static RenderType RENDER_TYPE = RenderType.JSP;
    
    /*
     * 默认URL后缀
     */
    public static String URL_EXT = ".jsp";
    
    /*
     * 默认物理视图后缀
     */
    public static String VIEW_EXT = ".jsp";
    
    /*
     * 默认连接池
     */
    public static String POOL_TYPE = "druid";

    /*
     * 默认配置路径
     */
    public static String CONFIG_PATH = null;

    /*
     * 默认文件编码
     */
    public static String ENCODING = "utf-8";

    /*
     * 全局过滤器
     */
    public static String GLOBAL_INTERCEPTOR = null;

    /*
     * 要扫描的包
     */
    public static String BASE_PACKAGE = null;

    private Const() {
    }

    public static void init() {
        // 加载配置
        Map<String, String> m = PropUtil.getPropertyMap(CONFIG_PATH);
        if (null != m && !m.isEmpty()) {
            CONST_MAP.putAll(m);
            if (null != m && !m.isEmpty()) {
                // 编码
                if (StringUtils.isNotBlank(m.get("unique.encoding"))) {
                    Const.ENCODING = m.get("unique.encoding").trim().toLowerCase();
                }
                // 视图类型
                if (StringUtils.isNotBlank(m.get("unique.viewType"))) {
                    // jetbrick-template
                    if (m.get("unique.viewType").trim().equalsIgnoreCase(RenderType.JET.toString())) {
                        Const.RENDER_TYPE = RenderType.JET;
                    }
                    // httl-template
                    if (m.get("unique.viewType").trim().equalsIgnoreCase(RenderType.HTTL.toString())) {
                        Const.RENDER_TYPE = RenderType.HTTL;
                    }
                    // beetl-template
                    if (m.get("unique.viewType").trim().equalsIgnoreCase(RenderType.BEETL.toString())) {
                        Const.RENDER_TYPE = RenderType.BEETL;
                    }
                    // URL后缀
                    if (StringUtils.isNotBlank(m.get("unique.urlext"))) {
                        Const.URL_EXT = m.get("unique.urlext").trim();
                    }
                    // 视图后缀
                    if (StringUtils.isNotBlank(m.get("unique.viewext"))) {
                        Const.VIEW_EXT = m.get("unique.viewext").trim();
                    }
                }
                // 全局拦截器
                if (StringUtils.isNotBlank(m.get("unique.globalInterceptor"))) {
                    Const.GLOBAL_INTERCEPTOR = m.get("unique.globalInterceptor").trim();
                }
                // 要扫描bean的包
                if (StringUtils.isNotBlank(m.get("unique.beanspackage"))) {
                    Const.BASE_PACKAGE = m.get("unique.beanspackage").trim();
                }
                // 是否启用redis
                if (StringUtils.isNotBlank(m.get("redis.pool"))) {
                    Const.REDIS_IS_OPEN = Boolean.valueOf(m.get("redis.pool").trim());
                }
                // 数据库连接池类型
                if (StringUtils.isNotBlank(m.get("unique.db.pool"))) {
                    Const.POOL_TYPE = m.get("unique.db.pool").trim();
                }
            }
        }
    }

    public boolean getDevMode() {
        return false;
    }

    public void setErrorView(int errorCode, String errorView) {
        ERRPR_VIEWMAPPING.put(errorCode, errorView);
    }

    public static String getErrorView(int errorCode) {
        return ERRPR_VIEWMAPPING.get(errorCode);
    }

}
