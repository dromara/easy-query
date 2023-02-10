package org.jdqc.core.impl;

import org.jdqc.core.abstraction.lambda.SqlExpression;
import org.jdqc.core.abstraction.sql.Select0;
import org.jdqc.core.abstraction.sql.base.ColumnSelector;
import org.jdqc.core.abstraction.sql.base.SqlColumnAsSelector;
import org.jdqc.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.core.abstraction.sql.enums.PredicateModeEnum;
import org.jdqc.core.impl.lambda.DefaultSqlColumnAsSelector;
import org.jdqc.core.impl.lambda.DefaultSqlColumnSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: AbstractSelect0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:44
 * @Created by xuejiaming
 */
public abstract class AbstractSelect0<T1, TChain> implements Select0<T1, TChain> {
    protected final Class<T1> t1Class;
    private final SelectContext selectContext;

    public AbstractSelect0(Class<T1> t1Class, SelectContext selectContext) {
        this.t1Class = t1Class;

        this.selectContext = selectContext;
    }


    @Override
    public abstract int count();

    @Override
    public abstract boolean any();

    @Override
    public T1 firstOrNull() {
        SqlExpression<SqlColumnSelector<T1>> selectExpression = ColumnSelector::columnAll;
        return firstOrNull(selectExpression);
    }

    @Override
    public T1 firstOrNull(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        this.take(1);
        List<T1> list = toList(selectExpression);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass) {
        SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression = ColumnSelector::columnAll;
        return firstOrNull(resultClass, selectExpression);
    }

    @Override
    public <TR> TR firstOrNull(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
        this.take(1);
        List<TR> list = toList(resultClass, selectExpression);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<T1> toList() {
        SqlExpression<SqlColumnSelector<T1>> selectorExpression = ColumnSelector::columnAll;
        return toList(selectorExpression);
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass) {
        SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression=ColumnSelector::columnAll;
        return toList(resultClass,selectExpression);
    }

    @Override
    public <TR> List<TR> toList(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
        StringBuilder select = new StringBuilder();
        DefaultSqlColumnAsSelector<T1, TR> selector = new DefaultSqlColumnAsSelector<>(0, getSelectContext(),select);
        selectExpression.apply(selector);
        List<T1> list = toInternalList(select.toString());
        //todo t1 2 tr
        return new ArrayList<>();
    }

    @Override
    public List<T1> toList(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        StringBuilder select = new StringBuilder();
        SqlColumnSelector<T1> selector = new DefaultSqlColumnSelector<>(0,getSelectContext(),select);
        selectExpression.apply(selector);
        return toInternalList(select.toString());
    }

    /**
     * 子类实现方法
     *
     * @return
     */
    protected abstract List<T1> toInternalList(String columns);

    @Override
    public String toSql() {

        StringBuilder select = new StringBuilder();

        SqlExpression<SqlColumnSelector<T1>> selectExpression = ColumnSelector::columnAll;
        DefaultSqlColumnSelector<T1> selector = new DefaultSqlColumnSelector<>(0, getSelectContext(), select);
        selectExpression.apply(selector);
        return toSql(select.toString());
    }

    @Override
    public String toSql(SqlExpression<SqlColumnSelector<T1>> selectExpression) {
        StringBuilder select = new StringBuilder();
        DefaultSqlColumnSelector<T1> selector = new DefaultSqlColumnSelector<>(0, getSelectContext(), select);
        selectExpression.apply(selector);
        return toSql(select.toString());
    }

    @Override
    public <TR> String toSql(Class<TR> resultClass, SqlExpression<SqlColumnAsSelector<T1, TR>> selectExpression) {
        StringBuilder select = new StringBuilder();
        DefaultSqlColumnAsSelector<T1, TR> selector = new DefaultSqlColumnAsSelector<>(0, getSelectContext(), select);
        selectExpression.apply(selector);
        return toSql(select.toString());
    }

    @Override
    public abstract String toSql(String columns);

    protected abstract TChain getSelf();

    @Override
    public TChain where(boolean condition, SqlExpression<SqlPredicate<T1>> whereExpression) {
        if (condition) {
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
        if (condition) {
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
        if (condition) {
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
