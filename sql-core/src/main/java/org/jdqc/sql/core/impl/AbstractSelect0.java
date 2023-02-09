package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression;
import org.jdqc.sql.core.abstraction.sql.Select0;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.enums.PredicateModeEnum;

import java.util.List;

/**
 *
 * @FileName: AbstractSelect0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:44
 * @Created by xuejiaming
 */
public abstract class AbstractSelect0<T1,TChain> implements Select0<T1, TChain> {
    private final SelectContext selectContext;

    public AbstractSelect0(SelectContext selectContext){

        this.selectContext = selectContext;
    }


    @Override
    public abstract int count();

    @Override
    public abstract boolean any();

    @Override
    public abstract T1 firstOrNull();

    @Override
    public abstract List<T1> toList();

    @Override
    public abstract String toSql();
    protected abstract TChain getSelf();

    @Override
    public TChain where(boolean condition,SqlExpression<SqlPredicate<T1>> whereExpression) {
        if(condition){
            SqlPredicate<T1> sqlPredicate = getSelect1SqlPredicateProvider().getSqlPredicate1(PredicateModeEnum.WHERE_PREDICATE);
            whereExpression.apply(sqlPredicate);
        }
        return getSelf();
    }
//
//    @Override
//    public TChain select(boolean condition,SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
//        if(condition){
//            SqlColumnAsSelector<T1, TR> sqlSelector = getSelect1SqlPredicateProvider().getSqlSelector1();
//            selectExpression.apply(sqlSelector);
//        }
//        return getSelf();
//    }

    @Override
    public TChain groupBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        if(condition){
            SqlColumnSelector<T1> sqlGroupSelector1 = getSelect1SqlPredicateProvider().getSqlGroupSelector1();
            selectExpression.apply(sqlGroupSelector1);
        }
        return getSelf();
    }

    @Override
    public TChain orderByAsc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return getSelf();
    }

    @Override
    public TChain orderByDesc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return getSelf();
    }

    @Override
    public TChain skip(boolean condition, int skip) {
        if(condition){
            this.getSelectContext().setSkip(skip);
        }
        return getSelf();
    }

    @Override
    public TChain take(boolean condition, int take) {
        return getSelf();
    }


    public SelectContext getSelectContext() {
        return selectContext;
    }

    protected abstract Select1SqlProvider<T1> getSelect1SqlPredicateProvider();
}
