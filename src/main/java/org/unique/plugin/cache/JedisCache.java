package org.unique.plugin.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.unique.util.SerializeUtil;

/**
 * jedis tool
 * 
 * @author rex
 */
public class JedisCache extends RedisUtil implements Cache {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2282726405374609744L;

    @Override
    public String set(final String key, final String value) {
        return super.setString(key, value);
    }

    @Override
    public String set(final String key, final Serializable value) {
        return super.setObject(key, value);
    }

    @Override
    public String set(final String key, final String value, final int timeout) {
        return super.setString(key, value, timeout);
    }

    @Override
    public Long hset(final String key, final String field, final String value) {
        return super.hashSet(key, field, value);
    }

    @Override
    public Long hset(final String key, final String field, final String value, final int timeout) {
        return super.hashSet(key, field, value, timeout);
    }

    @Override
    public String hset(final String key, final Map<String, String> map) {
        return super.hashMultipleSet(key, map);
    }

    @Override
    public String hset(final String key, Map<String, String> map, final int timeout) {
        return super.hashMultipleSet(key, map, timeout);
    }

    @Override
    public Long hset(final String key, final String field, final Serializable value) {
        return super.hset(key, field, value);
    }

    @Override
    public Long hset(final String key, final String field, final Serializable value, int timeout) {
        return super.hset(key, field, value, timeout);
    }

    @Override
    public boolean exists(final String key, final String field) {
        return super.exists(key, field);
    }

    @Override
    public boolean exists(final String key) {
        return super.exists(key);
    }

    @Override
    public Long del(String key) {
        return super.delKey(key);
    }

    @Override
    public Long del(String key, String field) {
        return super.delKey(key, field);
    }

    @Override
    public <T extends Serializable> T get(String key) {
        return super.get(key);
    }

    @Override
    public final String get(String key, String field) {
        return super.hashGet(key, field);
    }

    @Override
    public Map<String, String> getAllHash(final String key) {
        return super.hashGetAll(key);
    }

    @Override
    public Serializable getModel(final String key, final String field) {
        return SerializeUtil.unserialize(super.hashGet(key, field).getBytes());
    }

    @Override
    public List<String> getSet(final String key, final Integer start, final Integer end) {
        return super.listRange(key, start, end);
    }

    @Override
    public Set<String> getKeys(final String pattern) {
        return super.getKeyLike(pattern);
    }

    @Override
    public Long delLike(final String patten) {
        return super.delKeysLike(patten);
    }

    @Override
    public String hget(final String key, final String field) {
        return super.hashGet(key, field);
    }

    @Override
    public String hget(final String key, final String field, final int timeout) {
        return super.hashGet(key, field, timeout);
    }

    @Override
    public String hget(String key, Map<String, String> map) {
        return super.hashMultipleSet(key, map);
    }

    @Override
    public Long hcount(String key) {
        return super.hashLen(key);
    }

    @Override
    public Long sadd(String key, String... members) {
        return super.sadd(key, members);
    }

    @Override
    public Long srem(String key, String... members) {
        return super.srem(key, members);
    }

    @Override
    public Set<String> sunion(String... keys) {
        return super.sunion(keys);
    }

    @Override
    public Set<String> sdiff(String... keys) {
        return super.sdiff(keys);
    }

    @Override
    public Long scard(String key) {
        return super.scard(key);
    }

    @Override
    public Long lpush(String key, String... values) {
        return super.listPushHead(key, values);
    }

    @Override
    public Long rpush(String key, String... values) {
        return super.listPushTail(key, values);
    }

    @Override
    public Long lpushTrim(String key, String value, long size) {
        return super.listPushHeadAndTrim(key, value, size);
    }

    @Override
    public Long ldel(String key, String value) {
        return super.listDel(key, value, -1);
    }

    @Override
    public Long llength(String key) {
        return super.listLen(key);
    }

}
