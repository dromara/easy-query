package com.easy.query.core.expression.parser.abstraction.internal;

import com.easy.query.core.expression.lambda.Property;

/**
 *
 * @FileName: SqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 * @author xuejiaming
 */
public interface ColumnSelector<T1,TChain> extends IndexAvailable {
    TChain column(Property<T1,?> column);
    TChain columnIgnore(Property<T1,?> column);
    TChain columnAll();
   default  <T2,TChain2> ColumnSelector<T2,TChain2> and(ColumnSelector<T2,TChain2> sub){
        return sub;
    }
}
