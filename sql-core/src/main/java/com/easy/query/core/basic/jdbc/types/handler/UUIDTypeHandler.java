package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * create time 2023/2/17 21:21
 * @author xuejiaming
 */
public class UUIDTypeHandler implements JdbcTypeHandler {
    public static final UUIDTypeHandler INSTANCE=new UUIDTypeHandler();
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getObject(jdbcProperty.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        Object value = parameter.getValue();
        parameter.getPs().setString(parameter.getIndex(), value==null? null :value.toString() );
    }
}
