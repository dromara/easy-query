package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: SqlDateTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:56
 * @author xuejiaming
 */
public class SqlDateTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getRs().getDate(resultSet.getIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setDate(parameter.getIndex(), (java.sql.Date) parameter.getValue());
    }

    @Override
    public int getJdbcType() {
        return Types.DATE;
    }
}
