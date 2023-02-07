package org.jdqc.sql.core.impl;

import org.jdqc.sql.core.abstraction.lambda.Property;
import org.jdqc.sql.core.abstraction.lambda.SqlExpression;
import org.jdqc.sql.core.abstraction.sql.base.WherePredicate;
import org.jdqc.sql.core.common.TableInfo;
import org.jdqc.sql.core.impl.lambda.DefaultSqlPredicate;
import org.jdqc.sql.core.util.StringKit;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SelectContext.java
 * @Description: 文件说明
 * @Date: 2023/2/6 12:39
 * @Created by xuejiaming
 */
public class SelectContext {
    private int skip;
    private int take;

    private final List<TableInfo> tableInfos;
    private final Class<?> resultClass;
    private final List<Class<?>> joinTables;

    private final StringBuilder where;

    public SelectContext(Class<?> mainClass,Class<?> resultClass){
        tableInfos=new ArrayList<>();
        joinTables=new ArrayList<>();
        tableInfos.add(new TableInfo(mainClass));
        this.resultClass=resultClass;
        this.where= new StringBuilder();
    }

    public List<TableInfo> getTableInfos() {
        return tableInfos;
    }
    public TableInfo getTableInfo(int index) {
        return tableInfos.get(index);
    }
    public Class<?> getResultClass() {
        return resultClass;
    }
    public List<Class<?>> getJoinTables() {
        return joinTables;
    }

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

    public StringBuilder getWhere() {
        return where;
    }
    public <T1> void where(SqlExpression<WherePredicate<T1>> whereExpression){
        DefaultSqlPredicate<T1> predicate = new DefaultSqlPredicate<>(1, this);
        whereExpression.apply(predicate);

    }

}
