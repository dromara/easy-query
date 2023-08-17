package com.easy.query.api4j.select.extension.queryable2;

import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.impl.SQLOrderByColumnSelectorImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderable2<T1, T2> extends ClientQueryable2Available<T1, T2>,Queryable2Available<T1, T2> {

    default Queryable2<T1, T2> orderByAsc(SQLExpression2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>> selectExpression) {
        getClientQueryable2().orderByAsc((selector1, selector2) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2));
        });
        return getQueryable2();
    }

    default Queryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>> selectExpression) {
        getClientQueryable2().orderByAsc(condition, (selector1, selector2) -> {
            selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2));
        });
        return getQueryable2();
    }

    default Queryable2<T1, T2> orderByAscMerge(SQLExpression1<Tuple2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

   default Queryable2<T1, T2> orderByAscMerge(boolean condition, SQLExpression1<Tuple2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>>> selectExpression){
        return orderByAsc(condition,(t,t1)->{
            selectExpression.apply(new Tuple2<>(t,t1));
        });
   }


    default Queryable2<T1, T2> orderByDesc(SQLExpression2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

   default Queryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>> selectExpression){
       getClientQueryable2().orderByDesc(condition, (selector1, selector2) -> {
           selectExpression.apply(new SQLOrderByColumnSelectorImpl<>(selector1), new SQLOrderByColumnSelectorImpl<>(selector2));
       });
        return getQueryable2();
   }

    default Queryable2<T1, T2> orderByDescMerge(SQLExpression1<Tuple2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

   default Queryable2<T1, T2> orderByDescMerge(boolean condition, SQLExpression1<Tuple2<SQLOrderBySelector<T1>, SQLOrderBySelector<T2>>> selectExpression){
        return orderByDesc(condition,(t,t1)->{
            selectExpression.apply(new Tuple2<>(t,t1));
        });
   }

}
