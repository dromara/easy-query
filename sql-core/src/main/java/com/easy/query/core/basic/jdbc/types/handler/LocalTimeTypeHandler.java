package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

/**
 * @author xuejiaming
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:21
 */
public class LocalTimeTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        Time time = resultSet.getStreamResult().getTime(resultSet.getIndex());
        if (time != null) {
            return time.toLocalTime();
        }
        return null;

    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        LocalTime localTime = (LocalTime) parameter.getValue();
        parameter.getPs().setTime(parameter.getIndex(), Time.valueOf(localTime));
    }
}
