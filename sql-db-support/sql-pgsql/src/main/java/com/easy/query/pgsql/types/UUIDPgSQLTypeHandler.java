package com.easy.query.pgsql.types;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import org.jetbrains.annotations.NotNull;

import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author xuejiaming
 * create time 2023/2/17 21:21
 */
public class UUIDPgSQLTypeHandler implements JdbcTypeHandler {
    public static final UUIDPgSQLTypeHandler INSTANCE = new UUIDPgSQLTypeHandler();

    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        Object object = streamResultSet.getObject(jdbcProperty.getJdbcIndex());
        if(object instanceof String){
            return UUID.fromString((String) object);
        }
        return object;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        Object value = parameter.getValue();
        if(value==null){
            parameter.getPs().setObject(parameter.getIndex(),null);
        }else{
            JDBCType jdbcType = getJdbcTType(parameter);
            if (jdbcType == JDBCType.VARCHAR) {
                parameter.getPs().setString(parameter.getIndex(), value.toString());
            } else {
                parameter.getPs().setObject(parameter.getIndex(), value, jdbcType.getVendorTypeNumber());
            }
        }
    }

    private @NotNull JDBCType getJdbcTType(EasyParameter parameter) {
        SQLParameter sqlParameter = parameter.getSQLParameter();
        if (sqlParameter != null) {
            return sqlParameter.getJdbcType();
        }

        return JDBCType.OTHER;
    }
}
