package org.unique.util;

import java.io.Serializable;

public class BaseKit {

	public static String getStr(String key, String defaultVal) {
        return key == null ? defaultVal : key;

    }
	
	public static String getStr(String key) {
		return getStr(key, "");
	}

	public static long getLong(String key) {
		return getLong(key, 0);
	}

	public static long getLong(String key, long defaultVal) {
		String val = getStr(key).trim();
		if ("".equals(val)) {
			return defaultVal;
		}
		return Long.parseLong(val);
	}

	public static int getInt(String key, int defaultVal) {
		String val = getStr(key).trim();
		if ("".equals(val)) {
			return defaultVal;
		}
		return Integer.parseInt(val);
	}

	public static int getInt(String key) {
		return getInt(key, 0);
	}

	public static boolean getBoolean(String key, boolean defaultVal) {
		String val = getStr(key).trim();
		if ("true".equalsIgnoreCase(val)) {
			return true;
		} else {
			return defaultVal;
		}
	}

	public static Object getObject(Object obj) {
		Serializable s = null;
		if(obj instanceof String){
			s = (String)obj;
		}
		if(obj instanceof Integer){
			s = (Integer)obj;
		}
		if(obj instanceof Double){
			s = (Double)obj;
		}
		if(obj instanceof Boolean){
			s = (Boolean)obj;
		}
		if(obj instanceof Long){
			s = (Long)obj;
		}
		if(obj instanceof java.util.Date){
			s = (java.util.Date)obj;
		}
		if(obj instanceof java.sql.Date){
			s = (java.sql.Date)obj;
		}
		return s;
	}

}
