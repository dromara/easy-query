package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.Clob;
import java.sql.SQLException;

/**
 * @FileName: CLobTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:54
 * @author xuejiaming
 */
public class CLobTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getClob(jdbcProperty.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setClob(parameter.getIndex(),(Clob) parameter.getValue());
    }

}
