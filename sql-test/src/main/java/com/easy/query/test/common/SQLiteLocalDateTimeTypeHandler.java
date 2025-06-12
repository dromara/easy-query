package com.easy.query.test.common;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2025/6/11 22:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteLocalDateTimeTypeHandler implements JdbcTypeHandler {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
//        String dateTimeStr = streamResultSet.getString(jdbcProperty.getJdbcIndex());
        Timestamp timestamp = streamResultSet.getTimestamp(jdbcProperty.getJdbcIndex());
        if (timestamp != null) {
            return timestamp.toLocalDateTime();
        }
        return null;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        LocalDateTime localDateTime = (LocalDateTime) parameter.getValue();
        String dateTimeStr = localDateTime.format(DEFAULT_FORMATTER);
        parameter.getPs().setString(parameter.getIndex(), dateTimeStr);
    }
}
