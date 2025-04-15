package com.easy.query.core.proxy.part.metadata;

import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;

/**
 * create time 2025/4/15 09:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class PartColumn {
    private final JdbcTypeHandler jdbcTypeHandler;
    private final Property<Object, ?> getterCaller;
    private final PropertySetterCaller<Object> setterCaller;

    public PartColumn(JdbcTypeHandler jdbcTypeHandler, Property<Object, ?> getterCaller, PropertySetterCaller<Object> setterCaller){
        this.jdbcTypeHandler = jdbcTypeHandler;
        this.getterCaller = getterCaller;
        this.setterCaller = setterCaller;
    }

    public JdbcTypeHandler getJdbcTypeHandler() {
        return jdbcTypeHandler;
    }

    public Property<Object,?> getGetterCaller() {
        return getterCaller;
    }

    public PropertySetterCaller<Object> getSetterCaller() {
        return setterCaller;
    }
}
