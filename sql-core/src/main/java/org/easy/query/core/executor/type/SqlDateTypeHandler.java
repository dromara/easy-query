package org.easy.query.core.executor.type;

import org.easy.query.core.executor.EasyParameter;
import org.easy.query.core.executor.EasyResultSet;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * @FileName: DateTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:56
 * @Created by xuejiaming
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
