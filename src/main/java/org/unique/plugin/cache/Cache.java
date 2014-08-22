package org.unique.plugin.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis api
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public interface Cache extends Serializable {

    /*------------key-------------*/
    boolean exists(String key, String field);

    boolean exists(String key);

    Long del(String key);

    Long delLike(String patten);

    Long del(String key, String field);

    <T extends Serializable> T get(String key);

    /*------------string-------------*/
    String set(String key, String value);

    String set(String key, Serializable value);

    String set(String key, String value, int timeout);

    String getString(String key);

    String get(String key, String field);

    Map<String, String> getAllHash(String key);

    Serializable getModel(String key, String field);

    List<String> getSet(String key, Integer start, Integer end);

    Set<String> getKeys(String pattern);

    /*------------hash-------------*/
    Long hset(String key, String field, String value);

    Long hset(String key, String field, String value, int timeout);

    String hset(String key, Map<String, String> map);

    String hset(String key, Map<String, String> map, int timeout);

    String hget(String key, String field);

    String hget(String key, String field, int timeout);

    String hget(String key, Map<String, String> map);

    Long hcount(String key);

    Long hset(String key, String field, Serializable value);

    Long hset(String key, String field, Serializable value, int timeout);

    /*------------list-------------*/

    Long lpush(String key, String... value);

    Long rpush(String key, String... value);
    
    Long ldel(String key, String value);
    
    Long llength(String key);

    Long lpushTrim(String key, String value, long size);

    List<String> listGetAll(String key);

    List<String> listRange(String key, long beginIndex, long endIndex);

    Map<String, List<String>> batchGetAllList(final List<String> keys);

    /*------------set-------------*/

    // 添加元素
    Long sadd(String key, String... values);

    // 获取key的集合
    Set<String> smembers(String key);

    // 删除
    Long srem(String key, String... members);

    // 是否关注
    Boolean sismember(String key, String value);

    // 统计key下的value个数
    Long scard(String key);

    // 交集
    Set<String> sinter(String... keys);

    // 并集
    Set<String> sunion(String... keys);

    // 差集
    Set<String> sdiff(String... keys);

}
