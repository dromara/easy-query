package com.easy.query.core.expression.builder;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/23 14:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AggregateFilter extends SQLNative<AggregateFilter> {
    QueryRuntimeContext getRuntimeContext();

    AggregateFilter func(TableAvailable table, ColumnFunction columnFunction, String property, SQLPredicateCompare compare, Object val);


    AggregateFilter and();

    AggregateFilter and(SQLExpression1<AggregateFilter> aggregateFilterSQLExpression);

    AggregateFilter or();

    AggregateFilter or(SQLExpression1<AggregateFilter> aggregateFilterSQLExpression);
}
