package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.builder.impl.SetterImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContextImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 */
public class ColumnSetterImpl<T> implements ColumnSetter<T> {
    protected final Setter setter;
    protected final TableAvailable table;

    public ColumnSetterImpl(TableAvailable table, EntityExpressionBuilder entityExpressionBuilder, SQLBuilderSegment sqlBuilderSegment) {
        this.setter = new SetterImpl(entityExpressionBuilder, sqlBuilderSegment);
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Setter getSetter() {
        return setter;
    }

    @Override
    public ColumnSetter<T> set(boolean condition, String property, Object val) {
        setter.set(condition,table,property,val);
        return this;
    }

    @Override
    public ColumnSetter<T> setWithColumn(boolean condition, String property1, String property2) {
        setter.setWithColumn(condition,table,property1,property2);
        return this;
    }

    @Override
    public ColumnSetter<T> setIncrementNumber(boolean condition, String property, Number val) {
        setter.setIncrementNumber(condition,table,property,val);
        return this;
    }

    @Override
    public ColumnSetter<T> setDecrementNumber(boolean condition, String property, Number val) {
        setter.setDecrementNumber(condition,table,property,val);
        return this;
    }

    @Override
    public ColumnSetter<T> setSQLSegment(String property, String sqlSegment, SQLExpression1<SQLNativePropertyExpressionContext> contextConsume) {
        setter.sqlNativeSegment(table,property,sqlSegment,c->{
            contextConsume.apply(new SQLNativePropertyExpressionContextImpl(table, c));
        });
        return this;
    }
}
