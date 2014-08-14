package org.unique.plugin.db.pool.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.unique.common.tools.IOUtil;
import org.unique.plugin.db.pool.DBPoolFactory;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * //TODO druid实现
 * 
 * @author renqi
 */
public class DruidPool implements DBPoolFactory {

    private static Logger logger = Logger.getLogger(DruidPool.class);

    private static DataSource _ds = null;

    private DruidPool() {
    } // singleton

    private static class DruidPoolHolder {

        public static DruidPool _databasePool = new DruidPool();
    }

    static {
        InputStream in = DruidPool.class.getResourceAsStream("/db.properties");
        Properties properties = new Properties();// 读取配置文件
        try {
            properties.load(in);
            IOUtil.closeQuietly(in);
            _ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e1) {
            logger.error(e1.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 得到一个数据库连接池实例
     * 
     * @return ConnectionPool 实例
     */
    public static DruidPool getInstance() {
        return DruidPoolHolder._databasePool;
    }

    @Override
    public DataSource getDataSource() {
        return _ds;
    }

}
