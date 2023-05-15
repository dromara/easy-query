package com.easy.query.core.basic.jdbc.executor.internal.merge.result.impl.memory.row;

/**
 * create time 2023/5/10 12:33
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ConstMemoryResultSetRow extends AbstractMemoryResultSetRow{
    private final Object[] data;
    public ConstMemoryResultSetRow(Object val){
        data= new Object[]{val};
    }

    @Override
    public Object[] getData() {
        return data;
    }
}
