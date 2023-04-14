package com.easy.query.core.sharding.merge.impl;

import com.easy.query.core.sharding.merge.abstraction.StreamResult;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * create time 2023/4/13 11:28
 * 文件说明
 *
 * @author xuejiaming
 */
public final class TransparentStreamResult implements StreamResult {
    private final StreamResult streamResult;

    public TransparentStreamResult(StreamResult streamResult){

        this.streamResult = streamResult;
    }
    @Override
    public boolean next() throws SQLException {
        return streamResult.next();
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return streamResult.getObject(columnIndex);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return streamResult.wasNull();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return streamResult.getMetaData();
    }

    @Override
    public void close() throws Exception {
        streamResult.close();
    }
}
