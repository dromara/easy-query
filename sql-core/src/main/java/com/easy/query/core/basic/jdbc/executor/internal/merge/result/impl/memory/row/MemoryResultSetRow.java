package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row;

/**
 * create time 2023/5/3 09:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MemoryResultSetRow {

     Object getValue(final int columnIndex);

     void setValue(final int columnIndex, final Object value);
}
