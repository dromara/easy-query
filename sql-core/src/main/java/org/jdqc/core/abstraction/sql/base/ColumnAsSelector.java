package org.jdqc.core.abstraction.sql.base;

import org.jdqc.core.abstraction.lambda.Property;

/**
 *
 * @FileName: SqlSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 * @Created by xuejiaming
 */
public interface ColumnAsSelector<T1,TR, TChain> extends ColumnSelector<T1,TChain> {
    TChain columnAs(Property<T1,?> column, Property<TR, ?> alias);
    TChain columnCount(Property<T1,?> column, Property<TR,?> alias);
    TChain columnSum(Property<T1,?> column, Property<TR,?> alias);
    TChain columnMax(Property<T1,?> column, Property<TR,?> alias);
    TChain columnMin(Property<T1,?> column, Property<TR,?> alias);
    TChain columnAvg(Property<T1,?> column, Property<TR,?> alias);
    TChain columnLen(Property<T1,?> column, Property<TR,?> alias);
    <T2,TChain2> ColumnAsSelector<T2,TR, TChain2> and(ColumnAsSelector<T2,TR, TChain2> sub);
}
