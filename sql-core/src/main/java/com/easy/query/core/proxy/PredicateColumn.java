package com.easy.query.core.proxy;

/**
 * create time 2023/7/12 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PredicateColumn<T> extends TableColumn {
    SQLPredicate ge(PredicateColumn<T> column);
    SQLPredicate ge(T val);
    SQLPredicate gt(PredicateColumn<T> column);
    SQLPredicate gt(T val);
    SQLPredicate eq(PredicateColumn<T> column);
   default SQLPredicate eq(T val){
       return eq(true,val);
   }
    SQLPredicate eq(boolean condition,T val);
    SQLPredicate ne(PredicateColumn<T> column);
    SQLPredicate ne(T val);
    SQLPredicate le(PredicateColumn<T> column);
    SQLPredicate le(T val);
    SQLPredicate lt(PredicateColumn<T> column);
    SQLPredicate lt(T val);
    SQLPredicate isNull();
    SQLPredicate isNotNull();
}
