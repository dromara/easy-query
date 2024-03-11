package com.easy.query.core.expression.builder;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunction;

import java.util.Arrays;
import java.util.Collection;

/**
 * create time 2023/6/23 14:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AggregateFilter extends SQLNative<AggregateFilter> {
    QueryRuntimeContext getRuntimeContext();

    AggregateFilter func0(TableAvailable table, ColumnFunction columnFunction, String property, SQLPredicateCompare compare, Object val);
    AggregateFilter func(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare compare, Object val);
    AggregateFilter func(TableAvailable table1, SQLFunction sqlFunction1, SQLPredicateCompare compare, TableAvailable table2,SQLFunction sqlFunction2);
    AggregateFilter func(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare compare, TableAvailable table2,String property);
    AggregateFilter func(TableAvailable table, SQLFunction sqlFunction,SQLPredicateCompare sqlPredicateAssert);
    <TProperty> AggregateFilter func(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare compare, Query<TProperty> subQuery);
    <TProperty> AggregateFilter func(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare compare, Collection<TProperty> collections);

    AggregateFilter like(TableAvailable leftTable, String property1, Object val,boolean like, SQLLikeEnum sqlLike);
    AggregateFilter like(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2,boolean like, SQLLikeEnum sqlLike);
    AggregateFilter like(TableAvailable leftTable, String property1, TableAvailable rightTable, SQLFunction sqlFunction,boolean like, SQLLikeEnum sqlLike);
    AggregateFilter like(TableAvailable leftTable, SQLFunction sqlFunction, Object val,boolean like, SQLLikeEnum sqlLike);
    AggregateFilter like(TableAvailable leftTable, SQLFunction sqlFunction, TableAvailable rightTable, String property2,boolean like, SQLLikeEnum sqlLike);
    AggregateFilter like(TableAvailable leftTable, SQLFunction sqlFunction1, TableAvailable rightTable, SQLFunction sqlFunction2,boolean like, SQLLikeEnum sqlLike);
    default <TProperty> AggregateFilter func(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare compare, TProperty[] arrays){
        return func(table,sqlFunction,compare, Arrays.asList(arrays));
    }


    AggregateFilter and();

    AggregateFilter and(SQLExpression1<AggregateFilter> aggregateFilterSQLExpression);

    AggregateFilter or();

    AggregateFilter or(SQLExpression1<AggregateFilter> aggregateFilterSQLExpression);
}
