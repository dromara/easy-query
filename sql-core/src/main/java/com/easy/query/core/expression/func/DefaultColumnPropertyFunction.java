package com.easy.query.core.expression.func;

/**
 * create time 2023/5/21 22:37
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultColumnPropertyFunction implements ColumnPropertyFunction{
    private final String propertyName;
    private final ColumnFunction columnFunction;

    public DefaultColumnPropertyFunction(String propertyName, ColumnFunction columnFunction){

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

    public static <T1, TR> ColumnPropertyFunction createDefault(String property, ColumnFunction columnFunction) {
        return new DefaultColumnPropertyFunction(property, columnFunction);
    }
}
