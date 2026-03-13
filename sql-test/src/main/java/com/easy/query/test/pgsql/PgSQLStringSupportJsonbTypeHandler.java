package com.easy.query.test.pgsql;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyClassUtil;
import org.jetbrains.annotations.NotNull;
import org.postgresql.util.PGobject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * create time 2023/2/17 21:21
 */
public class PgSQLStringSupportJsonbTypeHandler implements JdbcTypeHandler {
    public static final PgSQLStringSupportJsonbTypeHandler INSTANCE = new PgSQLStringSupportJsonbTypeHandler();

    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getString(jdbcProperty.getJdbcIndex());
    }

    private void setJsonParameter(EasyParameter parameter) throws SQLException {

        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue((String) parameter.getValue());
        parameter.getPs().setObject(parameter.getIndex(), pGobject);
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {

        JDBCType jdbcType = parameter.getSQLParameter().getJdbcType();
//
        if (jdbcType == JDBCType.JAVA_OBJECT) {
            setJsonParameter(parameter);
        } else {
            boolean json = isJsonOrJsonArray(parameter);
            if (json) {
                setJsonParameter(parameter);
            } else {
                parameter.getPs().setString(parameter.getIndex(), (String) parameter.getValue());
            }
        }
    }

    private boolean isJsonOrJsonArray(EasyParameter parameter) {
        ColumnMetadata columnMetadata = parameter.getSQLParameter().getColumnMetadata();
        if (columnMetadata != null) {
            return FieldUtil.isJsonObjectOrArray(columnMetadata.getEntityMetadata().getEntityClass(), columnMetadata.getPropertyType(), columnMetadata.getPropertyName());
        }
        return false;
    }

}
