package com.easy.query.core.basic.jdbc.con.impl;

import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.con.EasyDataSourceConnection;
import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.util.EasyCollectionUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/5/11 23:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyDataSourceConnection implements EasyDataSourceConnection {
    private static final Log log = LogFactory.getLog(DefaultEasyDataSourceConnection.class);
    private final Map<String, EasyConnection> connections = new HashMap<>();
    private final EasyQueryDataSource easyQueryDataSource;

    public DefaultEasyDataSourceConnection(EasyQueryDataSource easyQueryDataSource) {

        this.easyQueryDataSource = easyQueryDataSource;
    }

    @Override
    public Collection<EasyConnection> getConnections() {
        return connections.values();
    }

    @Override
    public EasyConnection getEasyConnectionOrNull(String dataSource) {
        return connections.get(dataSource);
    }

    @Override
    public void putIfAbsent(String dataSource, EasyConnection easyConnection) {
        EasyConnection result = connections.put(dataSource, easyConnection);
        if (result != null) {
            throw new EasyQuerySQLException("repeat add easy connection:" + dataSource);
        }
    }

//    private EasyConnection internalGetEasyConnectionOrNull(String dataSource, Integer isolationLevel) {
//        try {
//            return new DefaultEasyConnection(dataSource, easyQueryDataSource.getDataSourceNotNull(dataSource).getConnection(), isolationLevel);
//        } catch (SQLException e) {
//            log.error("DefaultEasyDataSourceConnection.internalGetEasyConnectionOrNull.", e);
//            throw new EasyQuerySQLException(e);
//        }
//    }

    @Override
    public void setAutoCommit(boolean autoCommit) {
        for (EasyConnection connection : getConnections()) {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public void commit() {
        //todo 第一个错误那么就直接报错
        int connectionSize = getConnections().size();
        List<SQLException> exceptions = connectionSize > 1 ? new ArrayList<>(connectionSize) : null;
        int i = 0;
        for (EasyConnection connection : getConnections()) {
            try {

                connection.commit();
            } catch (SQLException ex) {
                log.error("commit error.", ex);
                if (i == 0) {
                    throw new EasyQuerySQLException(ex);
                } else {
                    assert exceptions != null;
                    exceptions.add(ex);
                }
            }
            i++;
        }
        throwAggregateSQLException(exceptions);
    }
    private void throwAggregateSQLException(Collection<SQLException> exceptions){
        if(EasyCollectionUtil.isEmpty(exceptions)){
            return;
        }
        SQLException ex = new SQLException("");
        exceptions.forEach(ex::setNextException);
        throw new EasyQuerySQLException(ex);
    }

    @Override
    public void rollback() {
        int connectionSize = getConnections().size();
        List<SQLException> exceptions = connectionSize > 0 ? new ArrayList<>(connectionSize) : null;
        for (EasyConnection connection : getConnections()) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("rollback error.", ex);
                assert exceptions != null;
                exceptions.add(ex);
            }
        }
        throwAggregateSQLException(exceptions);
    }

    @Override
    public void close() {
        for (EasyConnection connection : getConnections()) {
            try {
                connection.close();
            } catch (Exception ex) {
                log.error("close error ignored.", ex);
            }
        }
    }
}
