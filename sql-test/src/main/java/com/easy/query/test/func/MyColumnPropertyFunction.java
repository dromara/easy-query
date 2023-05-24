package com.easy.query.test.func;

import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;

/**
 * create time 2023/5/24 15:24
 * 文件说明
 *
 * @author xuejiaming
 */
public final class MyColumnPropertyFunction implements ColumnPropertyFunction {
    private final String propertyName;
    private final ColumnFunction columnFunction;

    public MyColumnPropertyFunction(String propertyName, ColumnFunction columnFunction){

        this.propertyName = propertyName;
        this.columnFunction = columnFunction;
    }
    @Override
    public ColumnFunction getColumnFunction() {
        return columnFunction;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
