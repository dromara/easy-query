package org.jdqc.sql.core.abstraction.sql.base;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: SqlSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 * @Created by xuejiaming
 */
public interface SqlSelector0<T1,TR, TChain> {
    TChain selectAll();
    TChain select(Property<T1,?> column);
    TChain selectIgnore(Property<T1,?> column);
    TChain selectAs(Property<T1,?> column, Property<TR, ?> alias);
    TChain selectCount(Property<T1,?> column, Property<TR,?> alias);
    TChain selectSum(Property<T1,?> column,Property<TR,?> alias);
    TChain selectMax(Property<T1,?> column,Property<TR,?> alias);
    TChain selectMin(Property<T1,?> column,Property<TR,?> alias);
    TChain selectAvg(Property<T1,?> column,Property<TR,?> alias);
    TChain selectLen(Property<T1,?> column,Property<TR,?> alias);
    <T2,TChain2> SqlSelector0<T2,TR, TChain2> and(SqlSelector0<T2,TR, TChain2> sub);
}
