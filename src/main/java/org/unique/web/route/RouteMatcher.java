package org.unique.web.route;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 路由适配器
 * 
 * @author rex
 */
public final class RouteMatcher {

    static final String[] EMPTY_STRINGS = new String[0];

    final String url;

    final Pattern pattern;

    final int[] orders;

    public RouteMatcher(String url) {
        this.url = url;
        StringBuilder sb = new StringBuilder(url.length() + 20);
        sb.append('^');
        int count = 0;
        Pattern p = Pattern.compile("/\\{[a-zA-Z0-9]+\\}");
        Matcher m = p.matcher(url);
        while (m.find()) {
            url = url.replaceAll("/\\{[a-zA-Z0-9]+\\}", "/([^/]*)");
            count++;
        }
        this.orders = new int[count];
        sb.append(url);
        sb.append('$');
        this.pattern = Pattern.compile(sb.toString());
    }

    public String[] getUrlParameters(String url) {
        Matcher m = pattern.matcher(url);
        if (!m.matches()) {
            return null;
        }
        if (orders.length == 0) {
            return EMPTY_STRINGS;
        }
        String[] params = new String[orders.length];
        for (int i = 0; i < orders.length; i++) {
            params[i] = m.group(i + 1);
        }
        return params;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof RouteMatcher) {
            return ((RouteMatcher) obj).url.equals(this.url);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

}