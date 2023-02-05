package org.jdqc.sql.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectImpl.java
 * @Description: 文件说明
 * @Date: 2023/2/5 21:29
 * @Created by xuejiaming
 */
public class SelectImpl<T1, TR> implements Select<T1, TR> {
    private final Class<T1> clazz;
    private final Class<TR> trClass;
    private final List<Class<?>> joinTables;
    private final List<OnFunction<SqlPredicate<T1, ?>>> joinInvokes;

    private int skip;
    private int take;


    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public SelectImpl(Class<T1> clazz, Class<TR> trClass) {

        this.clazz = clazz;
        this.trClass = trClass;
        this.joinTables = new ArrayList<>();
        this.joinInvokes = new ArrayList<>();
    }

    public Class<T1> getClazz() {
        return clazz;
    }


    @Override
    public <R, S> Select<T1, TR> eq(Property<R, ?> column, Property<S, ?> val) {
        return Select.super.eq(column, val);
    }

    @Override
    public <R, S> Select<T1, TR> eq(boolean condition, Property<R, ?> column, Property<S, ?> val) {
        return null;
    }

    @Override
    public <R> Select<T1, TR> eq(boolean condition, Property<R, ?> column, Object val) {
        return null;
    }

    @Override
    public Select<T1, TR> skip(boolean condition, int skip) {
        return null;
    }

    @Override
    public Select<T1, TR> take(boolean condition, int take) {
        return null;
    }

    @Override
    public <T2> Select<T1, TR> leftJoin(Class<T2> joinClass, OnFunction<SqlPredicate<T1, ?>> on) {
        joinTables.add(joinClass);
        OnFunction<SqlPredicate<T1, ?>> join = on0 -> {
            SqlPredicate<T1, ?> apply = on.apply(on0);
            //软删除 多租户
            return apply;
        };
        this.joinInvokes.add(join);
        return this;
    }

    @Override
    public Select<T1, TR> selectAll(Class<?> selectClass) {
        return null;
    }

    @Override
    public Select<T1, TR> select(Property<T1, ?>... columns) {
        return null;
    }

    @Override
    public <TEntity> Select<T1, TR> selectAs(Property<TEntity, ?> column, Property<TR, ?> alias) {
        return null;
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
        return null;
    }

    @Override
    public List<TR> toList() {
        return null;
    }

    @Override
    public String toSql() {
        return null;
    }
}
