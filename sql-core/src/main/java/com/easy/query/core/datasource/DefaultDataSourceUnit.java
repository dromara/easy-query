package com.easy.query.core.datasource;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.common.SemaphoreReleaseOnlyOnce;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/5/27 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDataSourceUnit implements DataSourceUnit {
    protected final String dataSourceName;
    protected final DataSource dataSource;
    protected final Semaphore semaphore;

    public DefaultDataSourceUnit(String dataSourceName, DataSource dataSource, int dataSourcePool) {

        this.dataSourceName = dataSourceName;
        this.dataSource = dataSource;
        this.semaphore = dataSourcePool<=0?null:new Semaphore(dataSourcePool, true);
    }

    @Override
    public String getDataSourceName() {
        return dataSourceName;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public List<Connection> getConnections(int count, long timeout, TimeUnit unit) throws SQLException {
        if(semaphore==null){
            if(count>1){
                throw new EasyQueryInvalidOperationException("sharding table should set dataSourceName:["+dataSourceName+"] dataSourcePool,current value <= 0.");
            }
            return getConnections(count);
        }
        return getConnectionsLimit(count, timeout, unit);
    }

    protected List<Connection> getConnectionsLimit(int count, long timeout, TimeUnit unit) throws SQLException {
        SemaphoreReleaseOnlyOnce semaphoreReleaseOnlyOnce = tryAcquire(count, timeout, unit);
        if (semaphoreReleaseOnlyOnce == null) {
            throw new EasyQuerySQLException("dataSourceName:" + dataSourceName + " get connections:" + count + " busy.");
        }
        try {
            return getConnections(count);
        } finally {
            semaphoreReleaseOnlyOnce.release();
        }
    }

    protected List<Connection> getConnections(int count) throws SQLException {
        ArrayList<Connection> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Connection connection = dataSource.getConnection();
            result.add(connection);
        }
        return result;
    }

    protected SemaphoreReleaseOnlyOnce tryAcquire(int count, long timeout, TimeUnit unit) {

        try {
            boolean acquire = semaphore.tryAcquire(count, timeout, unit);
            if (acquire) {
                return new SemaphoreReleaseOnlyOnce(count, semaphore);
            }
            return null;
        } catch (InterruptedException e) {
            throw new EasyQueryException(e);
        }
    }
}
