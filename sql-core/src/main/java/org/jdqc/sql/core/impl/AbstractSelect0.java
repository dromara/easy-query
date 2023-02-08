package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression;
import org.jdqc.sql.core.abstraction.sql.Select0;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelector;
import org.jdqc.sql.core.abstraction.sql.enums.PredicateModeEnum;

import java.util.List;

/**
 *
 * @FileName: AbstractSelect0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:44
 * @Created by xuejiaming
 */
public abstract class AbstractSelect0<T1, TR,TChain> implements Select0<T1, TR, TChain> {
    private final SelectContext selectContext;

    public AbstractSelect0(SelectContext selectContext){

        this.selectContext = selectContext;
    }


    @Override
    public abstract int count();

    @Override
    public abstract boolean any();

    @Override
    public TR firstOrNull() {
        this.take(1);
        List<TR> list = toList();
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public abstract List<TR> toList();

    @Override
    public abstract String toSql();
    protected abstract TChain getChain();

    @Override
    public TChain where(boolean condition,SqlExpression<SqlPredicate<T1>> whereExpression) {
        if(condition){
            SqlPredicate<T1> sqlPredicate = getSelect1SqlPredicateProvider().getSqlPredicate1(PredicateModeEnum.WHERE_PREDICATE);
            whereExpression.apply(sqlPredicate);
        }
        return getChain();
    }

    @Override
    public TChain select(boolean condition,SqlExpression<SqlSelector<T1, TR>> selectExpression) {
        if(condition){
            SqlSelector<T1, TR> sqlSelector = getSelect1SqlPredicateProvider().getSqlSelector1();
            selectExpression.apply(sqlSelector);
        }
        return getChain();
    }

    @Override
    public TChain groupBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        if(condition){
            SqlColumnSelector<T1> sqlGroupSelector1 = getSelect1SqlPredicateProvider().getSqlGroupSelector1();
            selectExpression.apply(sqlGroupSelector1);
        }
        return getChain();
    }

    @Override
    public TChain orderByAsc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return getChain();
    }

    @Override
    public TChain orderByDesc(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        return getChain();
    }

    @Override
    public TChain skip(boolean condition, int skip) {
        if(condition){
            this.getSelectContext().setSkip(skip);
        }
        return getChain();
    }

    @Override
    public TChain take(boolean condition, int take) {
        return getChain();
    }


    public SelectContext getSelectContext() {
        return selectContext;
    }

    protected abstract Select1SqlProvider<T1,TR> getSelect1SqlPredicateProvider();
}
