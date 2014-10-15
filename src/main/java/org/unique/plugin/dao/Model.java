package org.unique.plugin.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.unique.common.tools.CollectionUtil;
import org.unique.common.tools.StringUtils;
import org.unique.plugin.cache.Cache;
import org.unique.plugin.cache.JedisCache;
import org.unique.plugin.db.exception.QueryException;
import org.unique.plugin.db.exception.UpdateException;
import org.unique.web.core.Const;

/**
 * base model
 * 
 * @author rex
 */
@SuppressWarnings({ "unchecked", "serial" })
public class Model<M extends Model<?>> implements Serializable {

	private static Cache redis;

	static {
		if (Const.REDIS_IS_OPEN) {
			if(null == redis){
				redis = new JedisCache();
			}
		}
	}

	public M find(String sql, Object... params) throws QueryException {
		if (Const.REDIS_IS_OPEN) {
			return this.findByCache(sql, params);
		}
		return (M) DB.find(this.getClass(), sql, params);
	}

	public M findByPK(Serializable pk) throws QueryException {
		String tableName = this.getClass().getAnnotation(Table.class).name();
		String pkName = this.getClass().getAnnotation(Table.class).PK();
		String sql = "select t.* from " + tableName + " t where t." + pkName + "=?";
		if (Const.REDIS_IS_OPEN) {
			return this.findByCache(sql, pk);
		}
		return (M) DB.find(this.getClass(), sql, pk);
	}

	public List<M> findList(String sql, Object... params) throws QueryException {
		if (Const.REDIS_IS_OPEN) {
			List<M> modelList = CollectionUtil.newArrayList();
			String tableName = this.getClass().getAnnotation(Table.class).name();
			String pkName = this.getClass().getAnnotation(Table.class).PK();
			List<Serializable> pks = DB.findColumnList("select t." + pkName + " from " + tableName + " t");
			String cacheSql = "select t.* from " + tableName + " t where t." + pkName + "=?";
			for (Serializable pk : pks) {
				M model = this.findByCache(cacheSql, pk);
				modelList.add(model);
			}
			return modelList;
		}
		return (List<M>) DB.findList(this.getClass(), sql, params);
	}

	public Page<M> findListPage(final int page, final int pageSize, final String sql, Object... params) throws QueryException {
		return (Page<M>) DB.findListPage(this.getClass(), page, pageSize, sql, params);
	}

	public List<Map<String, Object>> findMapList(final String sql, Object... params) throws QueryException {
		return DB.findMapList(sql, params);
	}
	
	public <T> T findColumn(final String sql, Object... params){
		return DB.findColumn(sql, params);
	}
	
	public <T> Map<T, Map<String, Object>> findColumnMap(final String column, final String sql, Object... params){
		return DB.findColumnMap(column, sql, params);
	}
	
	public <T> List<T> findColumnList(final String sql, Object... params){
		return DB.findColumnList(sql, params);
	}
	
	public Object[] findArray(final String sql, Object... params){
		return DB.findArray(sql, params);
	}
	
	public List<Object[]> findArrayList(final String sql, Object... params){
		return DB.findArrayList(sql, params);
	}

	public List<Map<String, Object>> findMapListPage(String sql, int page, int pageSize, Object... params)
			throws QueryException {
		return DB.findPage(sql, page, pageSize, params);
	}

	public int delete(String sql, Object... params) throws UpdateException {
		int count = DB.update(sql, params);
		if (Const.REDIS_IS_OPEN) {
			redis.delLike(this.getClass().getName() + ":");
		}
		return count;
	}

	public int delete(String sql, Serializable pk) throws UpdateException {
		int count = DB.update(sql, pk);
		if (Const.REDIS_IS_OPEN) {
			redis.del(this.getClass().getName() + ":" + pk);
		}
		return count;
	}
	
	public int delete(String sql, Serializable pk, Object... params) throws UpdateException {
		int count = DB.update(sql, params);
		if (Const.REDIS_IS_OPEN) {
			redis.del(this.getClass().getName() + ":" + pk);
		}
		return count;
	}

	public int deleteByPK(Serializable pk) throws UpdateException {
		String tableName = this.getClass().getAnnotation(Table.class).name();
		String pkName = this.getClass().getAnnotation(Table.class).PK();
		String sql = "delete from " + tableName + " t where t." + pkName + "=?";
		int count = DB.update(sql, pk);
		if (Const.REDIS_IS_OPEN) {
			if (Const.REDIS_IS_OPEN) {
				redis.del(this.getClass().getName() + ":" + pk);
			}
		}
		return count;
	}

	public int update(String sql, Object... params) throws UpdateException {
		int count = DB.update(sql, params);
		if (Const.REDIS_IS_OPEN) {
			redis.delLike(this.getClass().getName() + ":");
		}
		return count;
	}

	public int updateByPK(String sql, Serializable pk, Object... params) throws UpdateException {
		int count = DB.update(sql, params);
		if (Const.REDIS_IS_OPEN) {
			redis.del(this.getClass().getName() + ":" + pk);
		}
		return count;
	}
	
	public int insert(String sql, Object... params) throws UpdateException {
		return DB.update(sql, params);
	}

	/*----------------------------------缓存查询:S----------------------------------------*/

	private M findByCache(String sql, Serializable pk) {
		M model = null;
		if (redis.exists(this.getClass().getName() + ":" + pk)) {
			model = redis.get(this.getClass().getName() + ":" + pk);
		} else {
			model = (M) DB.find(this.getClass(), sql, pk);
			if(null != model){
				redis.set(this.getClass().getName() + ":" + pk, model);
			}
		}
		return model;
	}

	private M findByCache(String sql, Object... params) {
		M model = (M) DB.find(this.getClass(), sql, params);
		
		try {
			String pk = this.getClass().getAnnotation(Table.class).PK();
			if(StringUtils.isBlank(pk)){
				pk = "id";
			}
			Field field = this.getClass().getDeclaredField(pk);
			field.setAccessible(true);
			if(null != model && null != field.get(model)){
				redis.set(this.getClass().getName() + ":" + field.get(model), model);
			}
			field.setAccessible(false);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	/*----------------------------------缓存查询:E---------------------------------------*/

}
