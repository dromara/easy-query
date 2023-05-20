package com.easy.query.core.basic.api.select.provider;

import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLColumnResultSelector;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLGroupBySelector;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;

/**
 * create time 2023/5/20 11:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLExpressionProvider<TEntity> {
    SQLGroupBySelector<TEntity> getSQLGroupColumnSelector();
    SQLColumnSelector<TEntity> getSQLOrderColumnSelector(boolean asc);
    SQLWherePredicate<TEntity> getSQLWherePredicate();
    SQLWherePredicate<TEntity> getSQLAllPredicate();
    SQLAggregatePredicate<TEntity> getSQLAggregatePredicate();
    SQLWherePredicate<TEntity> getSQLOnPredicate();

    SQLColumnSelector<TEntity> getSQLColumnSelector(SQLBuilderSegment sqlSegment0Builder);
    <TR> SQLColumnAsSelector<TEntity,TR> getSQLColumnAsSelector(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);
    <TR> SQLColumnAsSelector<TEntity,TR> getSQLAutoColumnAsSelector(SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass);
    <TR> SQLColumnResultSelector<TEntity,TR> getSQLColumnResultSelector(SQLBuilderSegment sqlSegment0Builder);
}
