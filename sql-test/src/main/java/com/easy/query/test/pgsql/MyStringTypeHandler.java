package com.easy.query.test.pgsql;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import org.jetbrains.annotations.NotNull;
import org.postgresql.util.PGobject;

import java.sql.JDBCType;
import java.sql.SQLException;

/**
 * create time 2025/11/4 14:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyStringTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getString(jdbcProperty.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {


        JDBCType jdbcTType = getJdbcTType(parameter);
        if (jdbcTType == JDBCType.BINARY) {
            PGobject jsonObject = new PGobject();
            jsonObject.setType("jsonb");
            jsonObject.setValue((String) parameter.getValue());
            parameter.getPs().setObject(parameter.getIndex(), jsonObject);
        } else {
            parameter.getPs().setString(parameter.getIndex(), (String) parameter.getValue());
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
