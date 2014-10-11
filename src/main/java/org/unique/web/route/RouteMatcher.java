package org.unique.web.route;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * route matcher
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
@Deprecated
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
			//url = url.replaceAll("/\\{[a-zA-Z0-9]+\\}", "(/?[^/]*)");
			url = url.replaceAll("/\\{[a-zA-Z0-9]+\\}", "(/[0-9]+)");
			count++;
		}
		this.orders = new int[count];
		sb.append(url);
		sb.append('$');
		this.pattern = Pattern.compile(sb.toString());
	}

	public Pattern getPattern() {
		return this.pattern;
	}

	public String[] getUrlParameters(String url) {
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		Matcher m = pattern.matcher(url);
		if (!m.matches()) {
			return null;
		}
		if (orders.length == 0) {
			return EMPTY_STRINGS;
		}
		String[] params = new String[orders.length];
		for (int i = 0; i < orders.length; i++) {
			String p = m.group(i + 1).replace("/", "");
			if (isNumeric(p)) {
				params[i] = p;
			}
		}
		return params;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof RouteMatcher) {
			return ((RouteMatcher) obj).url.equals(this.url);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}

	private boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^-?[0-9]*$");
		return pattern.matcher(str).matches();
	}
	
	public static void main(String[] args) {
		String url = "/admin/music";
		Pattern p = Pattern.compile("/admin/music(/[0-9]+)");
		System.out.println(p.matcher(url).find());
	}

}