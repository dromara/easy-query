package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/2/8 00:10
 * @author xuejiaming
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
    public <TSubQuery> ColumnAsSelector<T1, TR> columnSubQueryAs(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc, String propertyAlias) {
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
    public ExpressionContext getExpressionContext() {
        return asSelector.getExpressionContext();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnAsSelector<T1, TR> column(String property) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TIncludeSource, TIncludeResult> ColumnAsSelector<T1, TR> columnInclude(boolean condition,String property, String aliasProperty, SQLActionExpression1<ColumnAsSelector<TIncludeResult, TIncludeResult>> includeSelectorExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(asSelector);
    }

    @Override
    public ColumnAsSelector<T1, TR> castChain() {
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnIgnore(String property) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ColumnAsSelector<T1, TR> columnAll() {
        asSelector.columnAll(table);
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, String propertyAlias) {
        throw new UnsupportedOperationException();
    }
}
