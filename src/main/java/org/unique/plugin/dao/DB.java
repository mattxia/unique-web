package org.unique.plugin.dao;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.BeanMapHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;
import org.unique.common.tools.CollectionUtil;
import org.unique.ioc.annotation.Component;
import org.unique.plugin.db.exception.QueryException;
import org.unique.plugin.db.exception.UpdateException;
import org.unique.plugin.db.pool.DBPoolFactory;
import org.unique.plugin.db.pool.POOL;
import org.unique.plugin.db.pool.impl.C3p0Pool;
import org.unique.plugin.db.pool.impl.DruidPool;
import org.unique.web.core.Const;

/**
 * //TODO 数据库操作类
 * 
 * @author rex
 */
@Component
public class DB {

    private static final Logger logger = Logger.getLogger(DB.class);

    // 数据源工厂
    private static DBPoolFactory dsFactory;
    
    static {
        if (Const.POOL_TYPE.equalsIgnoreCase(POOL.DRUID.toString())) {
            dsFactory = DruidPool.getInstance();
        } else if (Const.POOL_TYPE.equalsIgnoreCase(POOL.C3P0.toString())) {
            dsFactory = C3p0Pool.getInstance();
        } else {
            dsFactory = DruidPool.getInstance();
        }
        logger.debug(dsFactory);
    }

    /**
     * //TODO 查询实体对象
     * 
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public static <T> T find(Class<T> clazz, String sql, Object... params) {
        T entity;
        try {
            entity = getQueryRunner().query(sql, new BeanHandler<T>(clazz), params);
        } catch (SQLException e) {
            logger.error("queryModel.查询实体对象出错：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return entity;
    }
    
    @SuppressWarnings("rawtypes")
    private static ScalarHandler scalarHandler = new ScalarHandler() {  
        @Override  
        public Object handle(ResultSet rs) throws SQLException {  
            Object obj = super.handle(rs);  
            if (obj instanceof BigInteger)  
                return ((BigInteger) obj).longValue();  
            return obj;  
        }  
    }; 
    
    @SuppressWarnings("unchecked")
    public static long count(String sql, Object... params) {
        Number num = 0;
        try {
            num = (Number) getQueryRunner().query(sql, scalarHandler, params);
        } catch (SQLException e) {
            logger.error("count.统计数量错误：" + sql, e);
            throw new QueryException(e);
        }
        return (num != null) ? num.longValue() : -1;
    }

    /**
     * //TODO 查询实体list
     * 
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public static <T> List<T> findList(Class<T> clazz, String sql, Object... params) {
        List<T> entityList;
        try {
            entityList = getQueryRunner().query(sql, new BeanListHandler<T>(clazz), params);
        } catch (SQLException e) {
            logger.error("queryModelList.查询实体列表出错：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return entityList;
    }

    /**
     * //TODO 根据实体分页查询List
     * 
     * @param clazz
     * @param page
     * @param pageSize
     * @param sql
     * @param params
     * @return
     */
    public static <T> List<T> findList(Class<T> clazz, Integer page, Integer pageSize, String sql, Object... params) {
        List<T> entityList;
        try {
            sql = sql + " LIMIT ?,?";
            List<Object> list = Arrays.asList(params);
            if (null == params || params.length < 1) {
                list = CollectionUtil.newArrayList();
            }
            list.add(page);
            list.add(pageSize);
            params = list.toArray();
            entityList = getQueryRunner().query(sql, new BeanListHandler<T>(clazz), params);
        } catch (SQLException e) {
            logger.error("queryModelList.分页查询实体列表出错：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return entityList;
    }

    /**
     * //TODO 根据实体查询map
     * 
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public static <K, V> Map<K, V> findMap(Class<V> clazz, String sql, Object... params) {
        Map<K, V> entityMap;
        try {
            entityMap = getQueryRunner().query(sql, new BeanMapHandler<K, V>(clazz), params);
        } catch (SQLException e) {
            logger.error("queryModelMap.根据实体查询map出错：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return entityMap;
    }

    /**
     * //TODO 查询数组
     * 
     * @param sql
     * @param params
     * @return
     */
    public static Object[] findArray(String sql, Object... params) {
        Object[] array;
        try {
            array = getQueryRunner().query(sql, new ArrayHandler(), params);
        } catch (SQLException e) {
            logger.error("queryArray.查询数组出错：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return array;
    }

    /**
     * //TODO 查询数组list
     * 
     * @param sql
     * @param params
     * @return
     */
    public static List<Object[]> findArrayList(String sql, Object... params) {
        List<Object[]> arrayList;
        try {
            arrayList = getQueryRunner().query(sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            logger.error("queryArrayList.查询数组list出错：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return arrayList;
    }

    /**
     * //TODO 根据sql查询一个map
     * 
     * @param sql
     * @param params
     * @return
     */
    public static Map<String, Object> findMap(String sql, Object... params) {
        Map<String, Object> map;
        try {
            map = getQueryRunner().query(sql, new MapHandler(), params);
        } catch (SQLException e) {
            logger.error("queryMap.查询map出错：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return map;
    }

    /**
     * //TODO 根据sql查询List<Map>
     * 
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> findMapList(String sql, Object... params) {
        List<Map<String, Object>> fieldMapList;
        try {
            fieldMapList = getQueryRunner().query(sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("queryMapList.根据sql查询List<Map>：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return fieldMapList;
    }

    /**
     * //TODO 分页查询List<Map>
     * 
     * @param sql
     * @param page
     * @param pageSize
     * @param params
     * @return
     */
    public static List<Map<String, Object>> findPage(String sql, Integer page, Integer pageSize, Object... params) {
        List<Map<String, Object>> fieldMapList;
        try {
            sql = sql + " LIMIT ?,?";
            List<Object> list = Arrays.asList(params);
            if (null == params || params.length < 1) {
                list = CollectionUtil.newArrayList();
            }
            list.add(page);
            list.add(pageSize);
            params = list.toArray();
            fieldMapList = getQueryRunner().query(sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("queryMapListPage.map数据分页查询错误：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return fieldMapList;
    }

    /**
     * //TODO 查询一个列
     * 
     * @param sql
     * @param params
     * @return
     */
    public static <T> T findColumn(String sql, Object... params) {
        T entity;
        try {
            entity = getQueryRunner().query(sql, new ScalarHandler<T>(), params);
        } catch (SQLException e) {
            logger.error("queryColumn.查询一个列错误：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return entity;
    }

    /**
     * //TODO 查询多列
     * 
     * @param sql
     * @param params
     * @return
     */
    public static <T> List<T> findColumnList(String sql, Object... params) {
        List<T> list;
        try {
            list = getQueryRunner().query(sql, new ColumnListHandler<T>(), params);
        } catch (SQLException e) {
            logger.error("queryColumnList.查询多列错误：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return list;
    }

    /**
     * //TODO 将查询的列查为map
     * 
     * @param column
     * @param sql
     * @param params
     * @return
     */
    public static <T> Map<T, Map<String, Object>> findColumnMap(String column, String sql, Object... params) {
        Map<T, Map<String, Object>> map;
        try {
            map = getQueryRunner().query(sql, new KeyedHandler<T>(column), params);
        } catch (SQLException e) {
            logger.error("queryColumnMap.将查询的列查为map出错：" + sql, e);
            throw new QueryException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return map;
    }

    /**
     * //TODO 根据sql更新
     * 
     * @param sql
     * @param params
     * @return
     */
    public static int update(String sql, Object... params) {
        int result;
        try {
            result = getQueryRunner().update(sql, params);
        } catch (SQLException e) {
            logger.error("update.修改记录错误：" + sql, e);
            throw new UpdateException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + Arrays.toString(params));
        return result;
    }

    /**
     * //TODO 批量更新
     * 
     * @param sql
     * @param params
     * @return
     */
    public static int[] updateBatch(String sql, List<Object[]> list) {
        int[] result;
        try {
            Object[] param = list.toArray();
            Object[][] params = new Object[param.length][];
            result = getQueryRunner().batch(sql, params);
        } catch (SQLException e) {
            logger.error("update.批量修改记录错误" + sql, e);
            throw new UpdateException(e);
        }
        logger.debug("sql：" + sql + "\n  params：" + list.toString());
        return result;
    }
    
    /**
     * //TODO 获取queryRunner
     * @return
     */
    private static QueryRunner getQueryRunner() {
        return new QueryRunner(getDataSource());
    }

    /**
     * //TODO 获取数据源
     * @return
     */
    private static DataSource getDataSource() {
        return dsFactory.getDataSource();
    }

}
