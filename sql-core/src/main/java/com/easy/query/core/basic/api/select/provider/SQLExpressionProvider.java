package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.builder.core.ConditionAccepter;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.metadata.IncludeNavigateParams;

/**
 * create time 2023/5/20 11:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLExpressionProvider<TEntity> {
    ColumnGroupSelector<TEntity> getGroupColumnSelector();

    ColumnOrderSelector<TEntity> getOrderColumnSelector(boolean asc);

    WherePredicate<TEntity> getWherePredicate();

   <TR> NavigateInclude<TEntity> getNavigateInclude(IncludeNavigateParams includeNavigateParams);

    WherePredicate<TEntity> getAllWherePredicate();

    WhereAggregatePredicate<TEntity> getAggregatePredicate();

    WherePredicate<TEntity> getOnPredicate();

    ColumnSelector<TEntity> getColumnSelector(SQLBuilderSegment sqlSegment0Builder);

    <TR> ColumnAsSelector<TEntity, TR> getColumnAsSelector(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);

    <TR> ColumnAsSelector<TEntity, TR> getAutoColumnAsSelector(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);

    <TR> ColumnResultSelector<TEntity> getColumnResultSelector(SQLBuilderSegment sqlSegment0Builder);
}
