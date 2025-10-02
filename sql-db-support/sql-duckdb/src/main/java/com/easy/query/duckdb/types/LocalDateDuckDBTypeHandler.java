package com.easy.query.duckdb.types;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;

import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

/**
 * create time 2023/2/17 21:21
 * @author xuejiaming
 */
public class LocalDateDuckDBTypeHandler implements JdbcTypeHandler {
    public static final LocalDateDuckDBTypeHandler INSTANCE = new LocalDateDuckDBTypeHandler();
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        java.sql.Date sqlDate = streamResultSet.getDate(jdbcProperty.getJdbcIndex());
        if (sqlDate != null) {
            return sqlDate.toLocalDate();
        }
        return null;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        LocalDate localDate = (LocalDate) parameter.getValue();
        if (localDate != null) {
            parameter.getPs().setDate(parameter.getIndex(), java.sql.Date.valueOf(localDate));
        } else {
            parameter.getPs().setNull(parameter.getIndex(), Types.DATE);
        }
    }
}
