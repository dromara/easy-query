package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * create time 2023/2/17 21:21
 * @author xuejiaming
 */
public class LocalDateTimeTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        Timestamp timestamp = streamResultSet.getTimestamp(jdbcProperty.getJdbcIndex());
        if(timestamp!=null){
            return timestamp.toLocalDateTime();
        }
        return null;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        LocalDateTime localDateTime = (LocalDateTime) parameter.getValue();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        parameter.getPs().setTimestamp(parameter.getIndex(),timestamp);
    }
}
