package org.jdqc.sql.core.abstraction.sql.base;

import org.jdqc.sql.core.abstraction.lambda.Property;

/**
 *
 * @FileName: SqlSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 * @Created by xuejiaming
 */
public interface SqlColumnSelector2<T1,TR, TChain> extends SqlColumnSelector0<T1,TChain> {
    TChain columnAll();
    TChain columnIgnore(Property<T1,?> column);
    TChain columnAs(Property<T1,?> column, Property<TR, ?> alias);
    TChain columnCount(Property<T1,?> column, Property<TR,?> alias);
    TChain columnSum(Property<T1,?> column, Property<TR,?> alias);
    TChain columnMax(Property<T1,?> column, Property<TR,?> alias);
    TChain columnMin(Property<T1,?> column, Property<TR,?> alias);
    TChain columnAvg(Property<T1,?> column, Property<TR,?> alias);
    TChain columnLen(Property<T1,?> column, Property<TR,?> alias);
    <T2,TChain2> SqlColumnSelector2<T2,TR, TChain2> and(SqlColumnSelector2<T2,TR, TChain2> sub);
}
