package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:21
 * @author xuejiaming
 */
public class LocalDateTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        Timestamp timestamp = resultSet.getStreamResult().getTimestamp(resultSet.getIndex());
        if(timestamp!=null){
            return timestamp.toLocalDateTime().toLocalDate();
        }
        return null;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        LocalDate localDate = (LocalDate) parameter.getValue();
        long ts = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Timestamp timestamp = new Timestamp(ts);
        parameter.getPs().setTimestamp(parameter.getIndex(),timestamp);
    }
}
