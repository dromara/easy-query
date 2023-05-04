package com.easy.query.core.expression.parser.core.internal;

import com.easy.query.core.expression.lambda.Property;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupByColumnSelector<T1,TChain> extends IndexAvailable {
    TChain column(Property<T1,?> column);
    default  <T2,TChain2> ColumnSelector<T2,TChain2> and(ColumnSelector<T2,TChain2> sub){
        return sub;
    }
}
