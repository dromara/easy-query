package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.scec.NativeSQLPropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.NativeSQLPropertyExpressionContextImpl;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 */
public class ColumnSelectorImpl<T1> implements ColumnSelector<T1> {
    private final TableAvailable table;
    private final Selector selector;

    public ColumnSelectorImpl(TableAvailable table, Selector selector) {
        this.table = table;
        this.selector = selector;
    }

    @Override
    public Selector getSelector() {
        return selector;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnSelector<T1> column(String property) {
        selector.column(table, property);
        return this;
    }

    @Override
    public ColumnSelector<T1> sqlNativeSegment(String sqlSegment, SQLExpression1<NativeSQLPropertyExpressionContext> contextConsume) {
        selector.sqlNativeSegment(sqlSegment, context->{
            contextConsume.apply(new NativeSQLPropertyExpressionContextImpl(table,context));
        });
        return this;
    }

    @Override
    public ColumnSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        selector.columnFunc(table, columnPropertyFunction);
        return this;
    }

    @Override
    public ColumnSelector<T1> columnIgnore(String property) {
        selector.columnIgnore(table, property);
        return this;
    }

    @Override
    public ColumnSelector<T1> columnAll() {
        selector.columnAll(table);
        return this;
    }

}
