package org.unique.common.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集合工具类
 * @author:rex
 * @date:2014年8月14日
 * @version:1.0
 */
public class CollectionUtil {

    /**
     * 创建hashmap
     * 
     * @return
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * 创建concurrentHashMap
     * 
     * @return
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<K, V>();
    }

    /**
     * 创建arraylist
     * 
     * @return
     */
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    /**
     * 创建hashSet
     * 
     * @return
     */
    public static <T> HashSet<T> newHashSet() {
        return new HashSet<T>();
    }

    /**
     * 对map排序
     * 
     * @param map
     * @param compator
     * @return
     */
    public static <K, V> Map<K, V> sortMap(Map<K, V> map, Comparator<Entry<K, V>> compator) {
        Map<K, V> result = new LinkedHashMap<K, V>();
        List<Entry<K, V>> entries = new ArrayList<Entry<K, V>>(map.entrySet());
        Collections.sort(entries, compator);
        for (Entry<K, V> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    
    /**
     * 返回集合是否为空
     * @param c
     * @return
     */
    public static <E> boolean isEmpty(Collection<E> c){
        return (null == c || c.isEmpty());
    }
    
    /**
     * 返回map是否为空
     * @param map
     * @return
     */
    public static <K,V> boolean isEmpty(Map<K, V> map){
        return (null == map || map.isEmpty());
    }
}
