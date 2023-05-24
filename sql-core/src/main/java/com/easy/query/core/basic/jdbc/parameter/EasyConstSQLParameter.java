package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/28 20:47
 */
public final class EasyConstSQLParameter implements ConstSQLParameter {
    private final String propertyName;
    private final Object val;
    private final TableAvailable entityTable;

    public EasyConstSQLParameter(TableAvailable entityTable, String propertyName, Object val) {
        this.entityTable = entityTable;
        this.propertyName = propertyName;
        this.val = val;
    }

    @Override
    public TableAvailable getTableOrNull() {
        return entityTable;
    }

    @Override
    public String getPropertyNameOrNull() {
        return propertyName;
    }

    @Override
    public Object getValue() {
        return val;
    }
}
