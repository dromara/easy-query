//package com.easy.query.core.proxy.impl;
//
//import com.easy.query.core.expression.builder.Filter;
//import com.easy.query.core.proxy.SQLPredicate;
//
//import java.util.function.Consumer;
//
///**
// * create time 2023/7/12 21:48
// * 文件说明
// *
// * @author xuejiaming
// */
//public class SQLPredicateImpl implements SQLPredicate {
//
//
//    private final Consumer<Filter> filter;
//
//    public SQLPredicateImpl(Consumer<Filter> filter){
//        this.filter = filter;
//    }
//
//    @Override
//    public SQLPredicate and(SQLPredicate predicate) {
//        Consumer<Filter> f=f1->f1.and(predicate::accept);
//        return new SQLPredicateImpl(x->{
//            filter.accept(x);
//            f.accept(x);
//        });
//    }
//
//    @Override
//    public SQLPredicate or(SQLPredicate predicate) {
//        Consumer<Filter> f=f1->f1.or(predicate::accept);
//        return new SQLPredicateImpl(x->{
//            filter.accept(x);
//            f.accept(x);
//        });
//    }
//
//    @Override
//    public void accept(Filter f) {
//        filter.accept(f);
//    }
//}
