package org.jdqc.sql.core.impl.lambda;

import org.jdqc.sql.core.abstraction.lambda.Property;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.base.WherePredicate0;
import org.jdqc.sql.core.common.TableInfo;
import org.jdqc.sql.core.impl.SelectContext;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SqlWherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/7 06:58
 * @Created by xuejiaming
 */
public class DefaultSqlPredicate<T1> implements SqlPredicate<T1> {
    private final int index;
    private final SelectContext selectContext;

    public DefaultSqlPredicate(int index, SelectContext selectContext){
        this.index = index;
        this.selectContext = selectContext;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public DefaultSqlPredicate<T1> eq(boolean condition, Property<T1, ?> column, Object val) {
        TableInfo tableInfo = selectContext.getTableInfo(index());

        return null;
    }

    @Override
    public <T2, TChain2> DefaultSqlPredicate<T1> eq(boolean condition, WherePredicate0<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        return null;
    }

    @Override
    public <T2, TChain2> WherePredicate0<T2, TChain2> and(WherePredicate0<T2, TChain2> sub) {
        return null;
    }

    @Override
    public DefaultSqlPredicate<T1> and() {
        return null;
    }
}
