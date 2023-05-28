package com.easy.query.core.datasource;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.common.SemaphoreReleaseOnlyOnce;
import com.easy.query.core.expression.lambda.SQLSupplier;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
    private static Log log = LogFactory.getLog(DefaultDataSourceUnit.class);
    protected final String dataSourceName;
    protected final DataSource dataSource;
    protected final Semaphore semaphore;
    private final boolean warningBusy;

    public DefaultDataSourceUnit(String dataSourceName, DataSource dataSource, int dataSourcePool, boolean warningBusy) {

        this.dataSourceName = dataSourceName;
        this.dataSource = dataSource;
        this.semaphore = dataSourcePool <= 0 ? null : new Semaphore(dataSourcePool, true);
        this.warningBusy = warningBusy;
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
        if (semaphore == null) {
            if (count > 1) {
                throw new EasyQueryInvalidOperationException("sharding table should set dataSourceName:[" + dataSourceName + "] dataSourcePool,current value <= 0.");
            }
            Connection connection = getConnection();
            return Collections.singletonList(connection);
        }
        return tryGetConnection(count, timeout, unit, () -> getConnections(count));
    }

    @Override
    public Connection getConnection(long timeout, TimeUnit unit) throws SQLException {
        if (semaphore == null) {
            return getConnection();
        }
        return tryGetConnection(1, timeout, unit, this::getConnection);
    }

    protected <TR> TR tryGetConnection(int count, long timeout, TimeUnit unit, SQLSupplier<TR> supplier) throws SQLException {

        SemaphoreReleaseOnlyOnce semaphoreReleaseOnlyOnce = tryAcquire(count, timeout, unit);

        if (semaphoreReleaseOnlyOnce == null) {
            throw new EasyQuerySQLException("dataSourceName:" + dataSourceName + " get connections:" + 1 + " busy.");
        }
        try {
            return supplier.get();
        } finally {
            semaphoreReleaseOnlyOnce.release();
        }
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    protected List<Connection> getConnections(int count) throws SQLException {
        ArrayList<Connection> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Connection connection = getConnection();
            result.add(connection);
        }
        return result;
    }

    protected SemaphoreReleaseOnlyOnce tryAcquire(int count, long timeout, TimeUnit unit) {

        try {
            long startTime = warningBusy ? System.currentTimeMillis() : 0L;
            boolean acquire = semaphore.tryAcquire(count, timeout, unit);
            if (acquire) {
                if (warningBusy) {
                    long endTime = System.currentTimeMillis();
                    long constTime = endTime - startTime;
                    long timeoutMillis = unit.toMillis(timeout);
                    if (constTime >= (timeoutMillis * 0.8)) {
                        log.warn("get connection use time:" + constTime + "(ms),timeout:" + timeoutMillis + "(ms). you can try increasing the connection pool size or reducing the number of access requests.");
                    }
                }
                return new SemaphoreReleaseOnlyOnce(count, semaphore);
            }
            return null;
        } catch (InterruptedException e) {
            throw new EasyQueryException(e);
        }
    }

//    快超过时间阈值时进行提醒
//    long startTime = System.currentTimeMillis();
//    boolean acquire = semaphore.tryAcquire(count, timeout, unit);
//            if (acquire) {
//        long endTime = System.currentTimeMillis();
//        long constTime = endTime - startTime;
//        if(constTime>(unit.toMillis(timeout)*0.9)){
//
//        }
}
