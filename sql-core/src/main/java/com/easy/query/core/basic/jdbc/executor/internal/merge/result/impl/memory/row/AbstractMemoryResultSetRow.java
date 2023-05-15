package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row;

import com.easy.query.core.util.EasyCheck;

/**
 * create time 2023/5/10 12:34
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractMemoryResultSetRow implements MemoryResultSetRow{
    public abstract Object[] getData();
    public Object getValue(final int columnIndex) {
        Object[] data = getData();
        EasyCheck.assertArgumentElse(columnIndex > 0 && columnIndex < data.length + 1);
        return data[columnIndex - 1];
    }

    public void setValue(final int columnIndex, final Object value) {
        Object[] data = getData();
        EasyCheck.assertArgumentElse(columnIndex > 0 && columnIndex < data.length + 1);
        data[columnIndex - 1] = value;
    }
}
