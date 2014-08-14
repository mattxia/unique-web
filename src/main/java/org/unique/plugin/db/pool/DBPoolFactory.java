package org.unique.plugin.db.pool;

import javax.sql.DataSource;

/**
 * 
 * //TODO 数据源工厂
 * @author renqi
 */
public interface DBPoolFactory {

    DataSource getDataSource();
    
}
