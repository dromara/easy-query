package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.core.SQLAsNative;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * @author xuejiaming
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 */
public class ColumnAsSelectorImpl<T1, TR> implements ColumnAsSelector<T1, TR> {

    private final TableAvailable table;
    private final AsSelector asSelector;

    public ColumnAsSelectorImpl(TableAvailable table, AsSelector asSelector) {
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
    public <TIncludeSource, TIncludeResult> ColumnAsSelector<T1, TR> columnInclude(boolean condition,String property, String aliasProperty, SQLExpression1<ColumnAsSelector<TIncludeResult, TIncludeResult>> includeSelectorExpression) {
        if(condition){
            asSelector.columnInclude(table,property,aliasProperty,asSelector->{
                TableAvailable entityTable = asSelector.getEntityQueryExpressionBuilder().getTable(0).getEntityTable();
                includeSelectorExpression.apply(new ColumnAsSelectorImpl<>(entityTable,asSelector));
            });
        }
        return this;
    }

    @Override
    public <T> SQLAsNative<T> getSQLAsNative() {
        return EasyObjectUtil.typeCastNullable(asSelector);
    }

    @Override
    public ColumnAsSelector<T1, TR> castTChain() {
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
    public ColumnAsSelector<T1, TR> sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, String propertyAlias) {
        asSelector.sqlSegmentAs(sqlColumnSegment,propertyAlias);
        return this;
    }
}
