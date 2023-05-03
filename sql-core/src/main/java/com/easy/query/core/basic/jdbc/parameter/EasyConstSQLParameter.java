package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.expression.parser.abstraction.internal.EntityTableAvailable;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: ConstSqlParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 */
public final class EasyConstSQLParameter implements ConstSQLParameter {
    private final String propertyName;
    private final Object val;
    private final EntityTableAvailable entityTable;

    public EasyConstSQLParameter(EntityTableAvailable entityTable, String propertyName, Object val) {
        this.entityTable = entityTable;
        this.propertyName = propertyName;
        this.val = val;
    }

    @Override
    public EntityTableAvailable getTable() {
        return entityTable;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Object getValue() {
        return val;
    }
}
