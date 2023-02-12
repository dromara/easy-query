package org.easy.query.core.abstraction.sql.base;

import org.easy.query.core.abstraction.lambda.Property;

/**
 *
 * @FileName: SqlColumnSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 * @Created by xuejiaming
 */
public interface ColumnSelector<T1,TChain> extends IndexAware {
    TChain column(Property<T1,?> column);
    TChain columnAll();
    TChain columnIgnore(Property<T1,?> column);
   default  <T2,TChain2> ColumnSelector<T2,TChain2> and(ColumnSelector<T2,TChain2> sub){
        return sub;
    }
}
