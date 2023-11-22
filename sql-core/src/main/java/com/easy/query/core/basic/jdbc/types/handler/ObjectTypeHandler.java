package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @FileName: DefaultJdbcTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:17
 * @author xuejiaming
 */
public class ObjectTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getObject(jdbcProperty.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.setDefaultParameter();
    }
}
