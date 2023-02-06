package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.sql.*;
import org.jdqc.sql.core.abstraction.sql.base.*;

import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectBase.java
 * @Description: 文件说明
 * @Date: 2023/2/6 12:36
 * @Created by xuejiaming
 */
public abstract class SelectBase<T1, TR,Children> implements SqlFetch<TR>
        , SqlJoin<T1, TR>
        , SqlFilter<Children>{
    private final SelectContext<T1, TR> selectContext;

    public SelectBase(SelectContext<T1, TR> selectContext){

        this.selectContext = selectContext;
    }
    @Override
    public int count() {
        return 0;
    }

    @Override
    public boolean any() {
        return false;
    }

    @Override
    public TR firstOrNull() {
        this.skip(1);
        List<TR> list = toList();
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<TR> toList() {
        return null;
    }

    @Override
    public String toSql() {
        return null;
    }

    @Override
    public <T2> Select2a<T1, TR> leftJoin(Class<T2> joinClass, OnFunction<SqlPredicate2<T1, ?>> on) {
       leftJoin0(joinClass, on);
        return new Select2Impl<>(this.selectContext);
    }
    protected <T2> void leftJoin0(Class<T2> joinClass, OnFunction<SqlPredicate2<T1, ?>> on){
        OnFunction<SqlPredicate2<T1, ?>> join = on0 -> {
            SqlPredicate2<T1, ?> apply = on.apply(on0);
            //软删除 多租户
            return apply;
        };
        this.selectContext.getJoinTables().add(joinClass);
        this.selectContext.getJoinInvokes().add(join);
    }


    @Override
    public Children skip(boolean condition, int skip) {
        return null;
    }

    @Override
    public Children take(boolean condition, int take) {
        return null;
    }

    public SelectContext<T1,TR> getSelectContext() {
        return selectContext;
    }
}
