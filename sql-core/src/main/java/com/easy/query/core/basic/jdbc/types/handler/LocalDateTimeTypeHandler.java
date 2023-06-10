package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:21
 * @author xuejiaming
 */
public class LocalDateTimeTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        Timestamp timestamp = resultSet.getStreamResult().getTimestamp(resultSet.getIndex());
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
