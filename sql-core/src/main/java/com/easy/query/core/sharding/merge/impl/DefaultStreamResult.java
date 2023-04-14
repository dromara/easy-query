package com.easy.query.core.sharding.merge.impl;

import com.easy.query.core.sharding.merge.abstraction.StreamResult;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * create time 2023/4/13 11:34
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultStreamResult implements StreamResult {
    private final ResultSet resultSet;

    public DefaultStreamResult(ResultSet resultSet){

        this.resultSet = resultSet;
    }
    @Override
    public boolean next() throws SQLException {
        return resultSet.next();
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return resultSet.getObject(columnIndex);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return resultSet.wasNull();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return resultSet.getMetaData();
    }

    @Override
    public void close() throws Exception {
        resultSet.close();
    }
}
