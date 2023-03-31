package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.expression.sql.EntityTableExpression;

/**
 * @author xuejiaming
 * @FileName: ConstSqlParameter.java
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 */
public final class EasyConstSQLParameter implements ConstSQLParameter {
    private final EntityTableExpression table;
    private final String propertyName;
    private final Object val;

    public EasyConstSQLParameter(EntityTableExpression table, String propertyName, Object val) {
        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
    }

    @Override
    public EntityTableExpression getTable() {
        return table;
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
