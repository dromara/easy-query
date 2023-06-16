package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.GroupBySelector;
import com.easy.query.core.expression.parser.core.base.OrderBySelector;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;

/**
 * create time 2023/5/20 11:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLExpressionProvider<TEntity> {
    GroupBySelector<TEntity> getGroupColumnSelector();

    OrderBySelector<TEntity> getOrderColumnSelector(boolean asc);

    WherePredicate<TEntity> getWherePredicate();

    WherePredicate<TEntity> getAllWherePredicate();

    WhereAggregatePredicate<TEntity> getAggregatePredicate();

    WherePredicate<TEntity> getOnPredicate();

    ColumnSelector<TEntity> getColumnSelector(SQLBuilderSegment sqlSegment0Builder);

    <TR> ColumnAsSelector<TEntity, TR> getColumnAsSelector(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);

    <TR> ColumnAsSelector<TEntity, TR> getAutoColumnAsSelector(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);

    <TR> ColumnResultSelector<TEntity> getColumnResultSelector(SQLBuilderSegment sqlSegment0Builder);
}
