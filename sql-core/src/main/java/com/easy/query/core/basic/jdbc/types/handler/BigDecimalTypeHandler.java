package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:21
 * @author xuejiaming
 */
public class BigDecimalTypeHandler implements JdbcTypeHandler {

    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getBigDecimal(jdbcProperty.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setBigDecimal(parameter.getIndex(),(BigDecimal)parameter.getValue());
    }
}
