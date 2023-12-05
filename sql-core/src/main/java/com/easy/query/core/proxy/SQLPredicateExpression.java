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
    SQLPredicateExpression and(SQLPredicateExpression predicate);
    SQLPredicateExpression or(SQLPredicateExpression predicate);
    void accept(Filter f);

    SQLPredicateExpression empty=new SQLPredicateImpl(f->{});

//    static SQLPredicate and() {
//
//        Consumer<Filter> f= f1->f1.and();
//        return new SQLPredicateImpl(x->{
//            filter.accept(x);
//            f.accept(x);
//        });
//    }
}
