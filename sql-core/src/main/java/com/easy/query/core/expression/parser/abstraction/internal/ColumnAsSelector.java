package com.easy.query.core.expression.parser.abstraction.internal;

import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.lambda.Property;

/**
 *
 * @FileName: SqlSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 * @author xuejiaming
 */
public interface ColumnAsSelector<T1,TR, TChain> extends ColumnSelector<T1,TChain> {
    TChain columnAs(Property<T1,?> column, Property<TR, ?> alias);
   default TChain columnCount(Property<T1,?> column){
       return columnCount(column,null);
   }
   default TChain columnCount(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, EasyAggregate.COUNT);
   }
   default TChain columnDistinctCount(Property<T1,?> column){
       return columnDistinctCount(column,null);
   }
   default TChain columnDistinctCount(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, EasyAggregate.COUNT_DISTINCT);
   }
   default TChain columnSum(Property<T1,Number> column){
       return columnSum(column,null);
   }
   default TChain columnSum(Property<T1,Number> column, Property<TR,Number> alias){
       return columnFunc(column, alias, EasyAggregate.SUM);
   }
    default TChain columnMax(Property<T1,?> column){
        return columnMax(column,null);
    }
   default TChain columnMax(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, EasyAggregate.MAX);
   }
    default TChain columnMin(Property<T1,?> column){
        return columnMin(column,null);
    }
   default TChain columnMin(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, EasyAggregate.MIN);
   }
   default TChain columnAvg(Property<T1,Number> column){
       return columnAvg(column,null);
   }
   default TChain columnAvg(Property<T1,Number> column, Property<TR,Number> alias){
       return columnFunc(column, alias, EasyAggregate.AVG);
   }
   default TChain columnLen(Property<T1,?> column){
       return columnLen(column,null);
   }
   default TChain columnLen(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, EasyAggregate.LEN);
   }

    TChain columnFunc(Property<T1, ?> column, Property<TR, ?> alias, EasyFunc easyFunc);//EasyAggregate
    <T2,TChain2> ColumnAsSelector<T2,TR, TChain2> then(ColumnAsSelector<T2,TR, TChain2> sub);
}
