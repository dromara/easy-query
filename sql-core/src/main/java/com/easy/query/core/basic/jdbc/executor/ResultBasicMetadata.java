package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;

/**
 * create time 2023/8/12 14:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class ResultBasicMetadata {
    private final String columnName;
    private final DataReader dataReader;
    private final JdbcTypeHandler jdbcTypeHandler;

    public  ResultBasicMetadata(String columnName,DataReader dataReader, JdbcTypeHandler jdbcTypeHandler){
        this.columnName = columnName;

        this.dataReader = dataReader;
        this.jdbcTypeHandler = jdbcTypeHandler;
    }

    public String getColumnName() {
        return columnName;
    }

    public DataReader getDataReader() {
        return dataReader;
    }

    public JdbcTypeHandler getJdbcTypeHandler() {
        return jdbcTypeHandler;
    }
}
