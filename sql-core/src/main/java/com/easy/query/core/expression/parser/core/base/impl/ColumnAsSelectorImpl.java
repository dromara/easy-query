package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 */
public class ColumnAsSelectorImpl<T1, TR> implements ColumnAsSelector<T1, TR> {


    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final TableAvailable table;
    private final AsSelector asSelector;

    public ColumnAsSelectorImpl(EntityQueryExpressionBuilder entityQueryExpressionBuilder, TableAvailable table, AsSelector asSelector) {
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.table = table;
        this.asSelector = asSelector;
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
        asSelector.column(table,property);
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnConstAs(String columnConst, String alias) {
        asSelector.columnConstAs(columnConst,alias);
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnIgnore(String property) {
        asSelector.columnIgnore(table,property);
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnAll() {
        asSelector.columnAll(table);
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnAs(String property, String propertyAlias) {
        asSelector.columnAs(table,property,propertyAlias);
        return this;
    }

    @Override
    public <TSubQuery> ColumnAsSelector<T1, TR> columnSubQueryAs(SQLFuncExpression1<WherePredicate<T1>,Query<TSubQuery>> subQueryableFunc, String propertyAlias) {
        WherePredicate<T1> sqlWherePredicate = new WherePredicateImpl<>(table,new FilterImpl(getRuntimeContext(),entityQueryExpressionBuilder.getExpressionContext(),entityQueryExpressionBuilder.getWhere(),false));
        Query<TSubQuery> subQueryQuery = subQueryableFunc.apply(sqlWherePredicate);
        asSelector.columnSubQueryAs(()->subQueryQuery,propertyAlias);
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, String propertyAlias) {
        asSelector.columnFuncAs(table,columnPropertyFunction,propertyAlias);
        return this;
    }
}
