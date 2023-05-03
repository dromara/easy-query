package com.easy.query.core.sharding.merge.result.impl;

import com.easy.query.core.sharding.merge.abstraction.StreamResultSet;
import com.easy.query.core.util.EasyCheck;

import java.sql.SQLException;

/**
 * create time 2023/5/3 09:55
 * 文件说明
 *
 * @author xuejiaming
 */
public final class MemoryResultSetRow {
    private final Object[] data;

    public MemoryResultSetRow(StreamResultSet streamResultSet,int columnCount) throws SQLException {
        data = load(streamResultSet,columnCount);
    }

    private Object[] load(StreamResultSet streamResultSet,int columnCount) throws SQLException {
        Object[] result = new Object[columnCount];
        for (int i = 0; i < columnCount; i++) {
            result[i] = streamResultSet.getObject(i + 1);
        }
        return result;
    }

    public Object getValue(final int columnIndex) {
        EasyCheck.assertArgumentElse(columnIndex > 0 && columnIndex < data.length + 1);
        return data[columnIndex - 1];
    }

    public void setValue(final int columnIndex, final Object value) {
        EasyCheck.assertArgumentElse(columnIndex > 0 && columnIndex < data.length + 1);
        data[columnIndex - 1] = value;
    }
}
