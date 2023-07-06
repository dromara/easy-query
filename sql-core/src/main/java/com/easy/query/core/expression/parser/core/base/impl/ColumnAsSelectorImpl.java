package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

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
    public ExpressionContext getExpressionContext() {
        return asSelector.getExpressionContext();
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
    public <TSubQuery> ColumnAsSelector<T1, TR> columnSubQueryAs(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc, String propertyAlias) {
        Query<TSubQuery> subQueryQuery = subQueryableFunc.apply();
        asSelector.columnSubQueryAs(()->subQueryQuery,propertyAlias);
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, String propertyAlias) {
        asSelector.columnFuncAs(table,columnPropertyFunction,propertyAlias);
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> sqlColumnAs(SQLColumnSegment sqlColumnSegment, String propertyAlias) {
        asSelector.sqlColumnAs(sqlColumnSegment,propertyAlias);
        return this;
    }
}
