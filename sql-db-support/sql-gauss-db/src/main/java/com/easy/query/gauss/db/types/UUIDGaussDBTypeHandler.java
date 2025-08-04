package com.easy.query.gauss.db.types;

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
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * create time 2023/2/17 21:21
 */
public class UUIDGaussDBTypeHandler implements JdbcTypeHandler {
    public static final UUIDGaussDBTypeHandler INSTANCE = new UUIDGaussDBTypeHandler();

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
            JDBCType jdbcTType = getJdbcTType(parameter);
            if (jdbcTType == JDBCType.VARCHAR) {
                parameter.getPs().setString(parameter.getIndex(), value.toString());
            } else {
                parameter.getPs().setObject(parameter.getIndex(), value, jdbcTType.getVendorTypeNumber());
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
