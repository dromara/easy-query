package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @FileName: TimestampTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/18 08:35
 * @author xuejiaming
 */
public class TimestampTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(JdbcProperty dataReader, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getTimestamp(dataReader.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setTimestamp(parameter.getIndex(), (Timestamp) parameter.getValue());
    }
}
