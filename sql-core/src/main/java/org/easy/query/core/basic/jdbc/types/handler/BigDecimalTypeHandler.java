package org.easy.query.core.basic.jdbc.types.handler;

import org.easy.query.core.basic.jdbc.types.EasyParameter;
import org.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:21
 * @Created by xuejiaming
 */
public class BigDecimalTypeHandler implements JdbcTypeHandler{
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getRs().getBigDecimal(resultSet.getIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setBigDecimal(parameter.getIndex(),(BigDecimal)parameter.getValue());
    }

    @Override
    public int getJdbcType() {
        return Types.DECIMAL;
    }
}
