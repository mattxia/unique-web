package org.unique.plugin.db.pool;

import javax.sql.DataSource;

/**
 * db pool
 * @author:rex
 * @date:2014年8月22日
 * @version:1.0
 */
public interface DBPool{

    DataSource getDataSource();
    
}
