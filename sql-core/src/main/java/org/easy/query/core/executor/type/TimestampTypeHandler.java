package org.easy.query.core.executor.type;

import org.easy.query.core.executor.EasyParameter;
import org.easy.query.core.executor.EasyResultSet;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * @FileName: TimestampTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/18 08:35
 * @Created by xuejiaming
 */
public class TimestampTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getRs().getTimestamp(resultSet.getIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setTimestamp(parameter.getIndex(), (Timestamp) parameter.getValue());
    }

    @Override
    public int getJdbcType() {
        return Types.TIMESTAMP;
    }
}
