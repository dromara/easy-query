package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * @FileName: TimestampTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/18 08:35
 * @author xuejiaming
 */
public class TimestampTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getStreamResult().getTimestamp(resultSet.getIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setTimestamp(parameter.getIndex(), (Timestamp) parameter.getValue());
    }
}
