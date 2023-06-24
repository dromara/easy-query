package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * @author xuejiaming
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 */
public class ColumnAutoAsSelectorImpl<T1, TR> implements ColumnAsSelector<T1, TR> {


    private final TableAvailable table;
    private final AsSelector asSelector;

    public ColumnAutoAsSelectorImpl(TableAvailable table, AsSelector asSelector) {
        this.table = table;
        this.asSelector = asSelector;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnAs(String property, String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TSubQuery> ColumnAsSelector<T1, TR> columnSubQueryAs(SQLFuncExpression1<WherePredicate<T1>, Query<TSubQuery>> subQueryableFunc, String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AsSelector getAsSelector() {
        return asSelector;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return asSelector.getRuntimeContext();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnAsSelector<T1, TR> column(String property) {
        return null;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnConstAs(String columnConst, String alias) {
        return null;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnIgnore(String property) {
        return null;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnAll() {
        asSelector.columnAll(table);
        return this;
    }
    @Override
    public ColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, String propertyAlias) {
        throw new UnsupportedOperationException();
    }
}
