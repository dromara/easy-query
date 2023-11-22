package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @Description: 文件说明
 * @Date: 2023/2/17 21:56
 * @author xuejiaming
 */
public class SQLDateTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getDate(jdbcProperty.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setDate(parameter.getIndex(), (java.sql.Date) parameter.getValue());
    }
}
