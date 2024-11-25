package com.easy.query.core.basic.extension.navigate;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2024/3/1 15:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface NavigateExtraFilterStrategy {
//   /**
//    *
//    * @param builder
//    * @return
//    */
//   default SQLExpression2<WherePredicate<?>, EntitySQLTableOwner<?>> getPredicateExtraFilterExpression(NavigateBuilder builder){
//      return (where,table)->{
//         SQLExpression1<WherePredicate<?>> predicateFilterExpression = getPredicateFilterExpression(builder);
//         predicateFilterExpression.apply(where);
//      };
//   }
   //EntitySQLTableOwner<?> selfTableOrNull
   @Nullable
   SQLExpression1<WherePredicate<?>> getPredicateFilterExpression(NavigateBuilder builder);
   @Nullable
   SQLExpression1<WherePredicate<?>> getPredicateManyToManyFilterExpression(NavigateBuilder builder);
}
