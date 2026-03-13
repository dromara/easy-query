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

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {

        JDBCType jdbcType = parameter.getSQLParameter().getJdbcType();
//
        if (jdbcType == JDBCType.JAVA_OBJECT) {
            PGobject pGobject = new PGobject();
            pGobject.setType("jsonb");
            pGobject.setValue((String) parameter.getValue());
            parameter.getPs().setObject(parameter.getIndex(), pGobject);
        }else{
            boolean json = isJsonOrJsonArray(parameter);//不使用@Column的jdbcType属性直接判断ColumnMetadata的类型
            if(json){
                PGobject pGobject = new PGobject();
                pGobject.setType("jsonb");
                pGobject.setValue((String) parameter.getValue());
                parameter.getPs().setObject(parameter.getIndex(), pGobject);
            }else{
                parameter.getPs().setString(parameter.getIndex(), (String)parameter.getValue());
            }
        }
    }

    private boolean isJsonOrJsonArray(EasyParameter parameter){
        ColumnMetadata columnMetadata = parameter.getSQLParameter().getColumnMetadata();
        if(columnMetadata!=null){
            if(JsonObject.class.isAssignableFrom(columnMetadata.getPropertyType())){
                return true;
            }
            return isJsonArray(columnMetadata.getEntityMetadata().getEntityClass(), columnMetadata.getPropertyType(), columnMetadata.getPropertyName());
        }
        return false;
    }

    //记得做缓存
    private boolean isJsonArray(@NotNull Class<?> entityClass, @NotNull Class<?> propertyType, String property) {
        if (List.class.isAssignableFrom(propertyType)) {
            Field field = EasyClassUtil.getFieldByName(entityClass, property);
            Type genericType = field.getGenericType();

            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();

                if (typeArguments.length > 0) {
                    Type elementType = typeArguments[0];
                    if (elementType instanceof Class) {
                        return JsonObject.class.isAssignableFrom((Class<?>) elementType);
                    }
                }
            }
        }
        return false;
    }

}
