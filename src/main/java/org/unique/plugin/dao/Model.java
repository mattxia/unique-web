package org.unique.plugin.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.unique.plugin.cache.Cache;
import org.unique.plugin.cache.JedisCache;
import org.unique.util.BaseKit;
import org.unique.web.core.Const;

/**
 * base model
 * 
 * @author rex
 */
@SuppressWarnings({ "unchecked", "serial"})
public class Model<M extends Model<?>> implements Serializable {

    private static Cache redis;

    static{
        if(Const.REDIS_IS_OPEN){
            redis = new JedisCache();
        }
    }
    
    private Object[] NULL_PARA_ARRAY = {};
    
    public M find(String sql, Object... params) {
        return (M) DB.find(this.getClass(), sql, params);
    }

    public List<M> findList(String sql, Object... params) {
        return (List<M>) DB.findList(this.getClass(), sql, params);
    }

    public List<Map<String, Object>> findMapList(String sql, Object... params) {
        return DB.findMapList(sql, params);
    }
    
    public int delete(String sql, Object... params) {
        return DB.update(sql, params);
    }

    public int update(String sql, Object... params) {
        int count = DB.update(sql, params);
        return count;
    }

    /**
     * 通知清除缓存
     */
    public void updateNotice() {
        // 清楚该表缓存
        redis.delLike(this.getClass().getSimpleName() + "_");
    }

    /**
     * 通知清除缓存
     */
    public void deleteNotice() {
        // 清楚该表缓存
        redis.delLike(this.getClass().getSimpleName() + "_");
    }

    /*---------------------------------cache---------------------------------*/

    private String sql2key(Integer pageNumber, Integer pageSize, String sql, Object... paras) {
        StringBuilder key = new StringBuilder(sql);
        if (null != pageNumber) {
            key.append(pageNumber);
        }
        if (null != pageSize) {
            key.append(pageSize);
        }
        if (null != paras && paras.length > 0) {
            for (Object object : paras) {
                if (null != object) {
                    key.append(BaseKit.getObject(object));
                }
            }
        }
        return DigestUtils.md5Hex(this.getClass().getSimpleName() + "_" + key.toString());
    }

    public M findByCache(String sql, Object... paras) {
        String key = sql2key(null, null, sql, null, paras);
        return findByCache(key, sql, paras);
    }

    public M findByCache(String key, String sql, Object... paras) {
        M model = null;
        if (redis.exists(key)) {
            model = redis.get(key);
        } else {
            model = (M) DB.find(this.getClass(), sql, paras);
            redis.set(key, model);
        }
        return model;
    }

    public M findByCache(int timeout, String sql, Object... paras) {
        List<M> result = findListByCache(timeout, sql, paras);
        return result.size() > 0 ? result.get(0) : null;
    }

    public M findByCache(String key, int timeout, String sql, Object... paras) {
        List<M> result = findListByCache(key, timeout, sql, paras);
        return result.size() > 0 ? result.get(0) : null;
    }

    public List<M> findListByCache(String sql, Object... paras) {
        String key = sql2key(null, null, sql, null, paras);
        return findListByCache(key, sql, paras);
    }

    public List<M> findListByCache(String key, String sql, Object... paras) {
        ArrayList<M> result = null;
        if (redis.exists(key)) {
            result = redis.get(key);
        } else {
            result = (ArrayList<M>) DB.findList(this.getClass(), sql, paras);
            redis.set(key, result);
        }
        return result;
    }

    public List<M> findListByCache(int timeout, String sql, Object... paras) {
        String key = sql2key(null, null, sql, null, paras);
        return findListByCache(key, timeout, sql, paras);
    }

    public List<M> findListByCache(String key, int timeout, String sql, Object... paras) {
        ArrayList<M> result = null;
        if (redis.exists(key)) {
            result = redis.get(key);
        } else {
            result = (ArrayList<M>) DB.findList(this.getClass(), sql, paras);
            redis.set(key, result);
        }
        return result;
    }

    public List<M> findListByCache(String sql) {
        return findListByCache(sql, NULL_PARA_ARRAY);
    }

    public List<M> findListByCache(String key, String sql) {
        return findListByCache(key, sql, NULL_PARA_ARRAY);
    }

    public List<M> findListByCache(String key, int timeout, String sql) {
        return findListByCache(key, timeout, sql, NULL_PARA_ARRAY);
    }

}
