package org.easy.query.core.executor.type;

import org.easy.query.core.executor.EasyParameter;
import org.easy.query.core.executor.EasyResultSet;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:21
 * @Created by xuejiaming
 */
public class LocalDateTimeTypeHandler implements JdbcTypeHandler{
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        Timestamp timestamp = resultSet.getRs().getTimestamp(resultSet.getIndex());
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

    @Override
    public int getJdbcType() {
        return Types.TIMESTAMP_WITH_TIMEZONE;
    }
}
