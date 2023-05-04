package com.easy.query.core.expression.parser.core;

import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 *
 * @FileName: SqlSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 * @author xuejiaming
 */
public interface SqlColumnAsSelector<T1,TR>  {

    TableAvailable getTable();
    SqlColumnAsSelector<T1,TR> column(Property<T1,?> column);
    SqlColumnAsSelector<T1,TR> columnIgnore(Property<T1,?> column);
    SqlColumnAsSelector<T1,TR> columnAll();
    SqlColumnAsSelector<T1,TR> columnAs(Property<T1,?> column, Property<TR, ?> alias);
   default SqlColumnAsSelector<T1,TR> columnCount(Property<T1,?> column){
       return columnCount(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnCount(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, EasyAggregate.COUNT);
   }
   default SqlColumnAsSelector<T1,TR> columnDistinctCount(Property<T1,?> column){
       return columnDistinctCount(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnDistinctCount(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, EasyAggregate.COUNT_DISTINCT);
   }
   default SqlColumnAsSelector<T1,TR> columnSum(Property<T1,Number> column){
       return columnSum(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnSum(Property<T1,Number> column, Property<TR,Number> alias){
       return columnFunc(column, alias, EasyAggregate.SUM);
   }
    default SqlColumnAsSelector<T1,TR> columnMax(Property<T1,?> column){
        return columnMax(column,null);
    }
   default SqlColumnAsSelector<T1,TR> columnMax(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, EasyAggregate.MAX);
   }
    default SqlColumnAsSelector<T1,TR> columnMin(Property<T1,?> column){
        return columnMin(column,null);
    }
   default SqlColumnAsSelector<T1,TR> columnMin(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, EasyAggregate.MIN);
   }
   default SqlColumnAsSelector<T1,TR> columnAvg(Property<T1,Number> column){
       return columnAvg(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnAvg(Property<T1,Number> column, Property<TR,Number> alias){
       return columnFunc(column, alias, EasyAggregate.AVG);
   }
   default SqlColumnAsSelector<T1,TR> columnLen(Property<T1,?> column){
       return columnLen(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnLen(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, EasyAggregate.LEN);
   }

    SqlColumnAsSelector<T1,TR> columnFunc(Property<T1, ?> column, Property<TR, ?> alias, EasyFunc easyFunc);//EasyAggregate
   default  <T2> SqlColumnAsSelector<T2,TR> then(SqlColumnAsSelector<T2,TR> sub){
       return sub;
   }
}
