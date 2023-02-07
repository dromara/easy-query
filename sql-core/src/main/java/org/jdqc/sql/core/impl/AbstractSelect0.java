package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.SqlExpression;
import org.jdqc.sql.core.abstraction.sql.Select0;
import org.jdqc.sql.core.abstraction.sql.base.SqlColumnSelector;
import org.jdqc.sql.core.abstraction.sql.base.SqlSelector;
import org.jdqc.sql.core.abstraction.sql.base.WherePredicate;

import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
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
    public TChain where(SqlExpression<WherePredicate<T1>> whereExpression) {
        selectContext.where(whereExpression);
        return getChain();
    }

    @Override
    public TChain select(SqlExpression<SqlSelector<T1, TR>> selectExpression) {
        return getChain();
    }

    @Override
    public TChain groupBy(boolean condition, SqlExpression<SqlColumnSelector<T1>> selectExpression) {
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
        this.getSelectContext()
        return getChain();
    }

    @Override
    public TChain take(boolean condition, int take) {
        return getChain();
    }


    public SelectContext<T1, TR> getSelectContext() {
        return selectContext;
    }
}
