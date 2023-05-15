package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.sql.SQLException;

/**
 * create time 2023/5/3 09:55
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultMemoryResultSetRow extends AbstractMemoryResultSetRow {
    private final Object[] data;

    public DefaultMemoryResultSetRow(StreamResultSet streamResultSet, int columnCount) throws SQLException {
        data = load(streamResultSet,columnCount);
    }

    private Object[] load(StreamResultSet streamResultSet,int columnCount) throws SQLException {
        Object[] result = new Object[columnCount];
        for (int i = 0; i < columnCount; i++) {
            result[i] = streamResultSet.getObject(i + 1);
        }
        return result;
    }

    @Override
    public Object[] getData() {
        return data;
    }
}
