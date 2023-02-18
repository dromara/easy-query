package org.easy.query.core.executor.type;

import org.easy.query.core.executor.EasyParameter;
import org.easy.query.core.executor.EasyResultSet;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;

/**
 * @FileName: TimeTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/18 08:36
 * @Created by xuejiaming
 */
public class TimeTypeHandler implements JdbcTypeHandler{
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getRs().getTime(resultSet.getIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setTime(parameter.getIndex(),(Time)parameter.getValue());
    }

    @Override
    public int getJdbcType() {
        return Types.TIME;
    }
}
