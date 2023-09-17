package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLColumn;

import java.util.Objects;

/**
 * create time 2023/6/22 13:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnImpl<TProxy,TProperty> implements SQLColumn<TProxy,TProperty> {
    private final TableAvailable table;
    private final String property;

    public SQLColumnImpl(TableAvailable table, String property){
        this.table = table;
        this.property = property;
    }

    @Override
    public TableAvailable getTable() {
        Objects.requireNonNull(table,"cant found table in sql context");
        return table;
    }

    @Override
    public String value() {
        return property;
    }
//
//    @Override
//    public SQLPredicate ge(PredicateColumn<TProperty> column) {
//        return null;
//    }
//
//    @Override
//    public SQLPredicate ge(TProperty val) {
//        return new SQLPredicateImpl(f->f.ge(this.table,this.property,val));
//    }
//
//    @Override
//    public SQLPredicate gt(PredicateColumn<TProperty> column) {
//        return null;
//    }
//
//    @Override
//    public SQLPredicate gt(TProperty val) {
//        return new SQLPredicateImpl(f->f.gt(this.table,this.property,val));
//    }
//
//    @Override
//    public SQLPredicate eq(PredicateColumn<TProperty> column) {
//        return new SQLPredicateImpl(f->f.eq(this.table,this.property,column.getTable(),column.value()));
//    }
//
//    @Override
//    public SQLPredicate eq(boolean condition,TProperty val) {
//        return new SQLPredicateImpl(f->{
//            if(condition){
//                f.eq(this.table,this.property,val);
//            }
//        });
//    }
//
//    @Override
//    public SQLPredicate ne(PredicateColumn<TProperty> column) {
//        return new SQLPredicateImpl(f->f.ne(this.table,this.property,column.getTable(),column.value()));
//    }
//
//    @Override
//    public SQLPredicate ne(TProperty val) {
//        return null;
//    }
//
//    @Override
//    public SQLPredicate le(PredicateColumn<TProperty> column) {
//        return null;
//    }
//
//    @Override
//    public SQLPredicate le(TProperty val) {
//        return null;
//    }
//
//    @Override
//    public SQLPredicate lt(PredicateColumn<TProperty> column) {
//        return null;
//    }
//
//    @Override
//    public SQLPredicate lt(TProperty val) {
//        return null;
//    }
//
//    @Override
//    public SQLPredicate isNull() {
//        return null;
//    }
//
//    @Override
//    public SQLPredicate isNotNull() {
//        return null;
//    }
}
