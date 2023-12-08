package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/7/12 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLPredicateExpression {
    void accept(Filter f);

//
//    default SQLPredicateExpression _concat(SQLPredicateExpression... predicate){
//        return _concat(true,predicate);
//    }
//    default SQLPredicateExpression _concat(boolean condition,SQLPredicateExpression... predicate){
//        SQLPredicateExpression[] concat = EasyArrayUtil.concat(new SQLPredicateExpression[]{this}, predicate);
//        return Predicate.and(condition,concat);
//    }
//    default SQLPredicateExpression _concatOr(SQLPredicateExpression... predicate){
//        return _concatOr(true,predicate);
//    }
//    default SQLPredicateExpression _concatOr(boolean condition,SQLPredicateExpression... predicate){
//        SQLPredicateExpression[] concat = EasyArrayUtil.concat(new SQLPredicateExpression[]{this}, predicate);
//        return Predicate.or(condition,concat);
//    }

//    static SQLPredicate and() {
//
//        Consumer<Filter> f= f1->f1.and();
//        return new SQLPredicateImpl(x->{
//            filter.accept(x);
//            f.accept(x);
//        });
//    }
}
