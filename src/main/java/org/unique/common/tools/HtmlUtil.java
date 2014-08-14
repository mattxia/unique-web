package org.unique.common.tools;

/**
 * html tool
 * @author:rex
 * @date:2014年8月14日
 * @version:1.0
 */
public class HtmlUtil {

    /**
     * html转义
     *
     * @param str
     * @return
     */
    public static String escapeHtml(String str) {
        if (null == str) return null;
        char[] cas = str.toString().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : cas) {
            switch (c) {
            case '&':
                sb.append("&amp;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '\'':
                sb.append("&#x27;");
                break;
            case '"':
                sb.append("&quot;");
                break;
            default:
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
