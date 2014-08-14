package org.unique.plugin.dao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.unique.common.tools.CollectionUtil;
import org.unique.common.tools.StringUtils;

/**
 * 生成sql类
 * 
 * @author rex
 */
public class SqlBase {

    private String baseSql;

    private StringBuffer paramSql;

    private List<Object> list;

    public SqlBase(String sql) {
        this.baseSql = sql;
        this.list = CollectionUtil.newArrayList();
        this.paramSql = new StringBuffer();
    }

    /**
     * 添加参数
     * 
     * @param sql
     * @param param
     */
    public void add(String sql, Object... param) {
        if (null != param && param.length > 0) {
            if (isNull(param[0])) {
                return;
            }
            this.paramSql.append("and " + sql);
            if (findStrCount(sql, "\\?") == 1) {
                this.list.add(param[0]);
                return;
            }
            for (int i = 0, len = param.length; i < len; i++) {
                if (isNotNull(param[i])) {
                    this.list.add(param[i]);
                }
            }
        }
    }

    public boolean isNotNull(Object param) {
        if (null == param) {
            return false;
        }
        if (param instanceof String) {
            return StringUtils.isNotBlank(param.toString());
        }
        return null != param;
    }

    public boolean isNull(Object param) {
        if (null == param) {
            return true;
        }
        if (param instanceof String) {
            return StringUtils.isBlank(param.toString());
        }
        return null == param;
    }

    public String getSQL() {
        if (this.list.size() > 0) {
            return this.baseSql + " where " + this.paramSql.toString().substring(4);
        }
        return this.baseSql;
    }

    public Object[] getParams() {
        return this.list.toArray();
    }

    /**
     * 查找某个keyword在srcText出现的次数
     * 
     * @param srcText
     * @param keyword
     * @return
     */
    private int findStrCount(String srcText, String keyword) {
        int count = 0;
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }
}
