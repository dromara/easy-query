package org.easy.query.core.executor.type;

import org.easy.query.core.executor.EasyParameter;
import org.easy.query.core.executor.EasyResultSet;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:21
 * @Created by xuejiaming
 */
public class LocalDateTypeHandler implements JdbcTypeHandler{
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        Timestamp timestamp = resultSet.getRs().getTimestamp(resultSet.getIndex());
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

    @Override
    public int getJdbcType() {
        return Types.DATE;
    }
}
