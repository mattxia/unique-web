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
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * collection util
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public class CollectionUtil {

    /**
     * new HashMap
     * @return
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    /**
     * new HashMap and initialCapacity
     * @param size
     * @return
     */
    public static <K, V> HashMap<K, V> newHashMap(int size) {
        return new HashMap<K, V>();
    }
    
    /**
     * new concurrentHashMap
     * 
     * @return
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<K, V>();
    }
    
    /**
     * new concurrentHashMap and initialCapacity
     * @param size
     * @return
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(int size) {
        return new ConcurrentHashMap<K, V>(size);
    }

    /**
     * new ArrayList
     * 
     * @return
     */
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }
    
    /**
     * new ArrayList and initialCapacity
     * @param size
     * @return
     */
    public static <T> ArrayList<T> newArrayList(int size) {
        return new ArrayList<T>(size);
    }

    /**
     * new HashSet
     * @return
     */
    public static <T> HashSet<T> newHashSet() {
        return new HashSet<T>();
    }

    /**
     * new HashSet and initialCapacity
     * @param size
     * @return
     */
    public static <T> HashSet<T> newHashSet(int size) {
        return new HashSet<T>(size);
    }
    
    /**
     * new TreeSet
     * @return
     */
    public static <T> TreeSet<T> newTreeSet() {
        return new TreeSet<T>();
    }
    
    /**
     * map sort
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
     * return collection is empty
     * @param c
     * @return
     */
    public static <E> boolean isEmpty(Collection<E> c){
        return (null == c || c.isEmpty());
    }
    
    /**
     * return map is empty
     * @param map
     * @return
     */
    public static <K,V> boolean isEmpty(Map<K, V> map){
        return (null == map || map.isEmpty());
    }
    
    /**
     * return array is empty
     * @param arr
     * @return
     */
    public static <T> boolean isEmpty(T[] arr){
    	return null == arr || arr.length == 0;
    }
}
