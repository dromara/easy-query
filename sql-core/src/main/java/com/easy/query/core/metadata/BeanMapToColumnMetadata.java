package com.easy.query.core.metadata;

import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;

/**
 * create time 2023/5/24 10:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class BeanMapToColumnMetadata {
    private final ColumnMetadata columnMetadata;
    private final JdbcTypeHandler jdbcTypeHandler;

    public BeanMapToColumnMetadata(ColumnMetadata columnMetadata, JdbcTypeHandler jdbcTypeHandler){

        this.columnMetadata = columnMetadata;
        this.jdbcTypeHandler = jdbcTypeHandler;
    }

    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    public JdbcTypeHandler getJdbcTypeHandler() {
        return jdbcTypeHandler;
    }
}
