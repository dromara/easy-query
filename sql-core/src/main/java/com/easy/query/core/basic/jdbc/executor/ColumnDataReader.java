package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/8/12 14:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnDataReader implements DataReader{
    private final int jdbcIndex;
    private final ColumnMetadata columnMetadata;

    public ColumnDataReader(int index, ColumnMetadata columnMetadata){

        this.jdbcIndex = index+1;
        this.columnMetadata = columnMetadata;
    }

    @Override
    public int getJdbcIndex() {
        return jdbcIndex;
    }

    @Override
    public Class<?> getPropertyType() {
        return columnMetadata.getPropertyType();
    }

    @Override
    public boolean isPrimitive() {
        return columnMetadata.isPrimitive();
    }
}
