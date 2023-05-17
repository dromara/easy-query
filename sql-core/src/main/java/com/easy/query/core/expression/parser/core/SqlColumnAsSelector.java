package com.easy.query.core.expression.parser.core;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnFunction;
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
    EasyQueryRuntimeContext getRuntimeContext();
    TableAvailable getTable();
    SqlColumnAsSelector<T1,TR> column(Property<T1,?> column);
    SqlColumnAsSelector<T1,TR> columnIgnore(Property<T1,?> column);
    SqlColumnAsSelector<T1,TR> columnAll();
    SqlColumnAsSelector<T1,TR> columnAs(Property<T1,?> column, Property<TR, ?> alias);
   default SqlColumnAsSelector<T1,TR> columnCount(Property<T1,?> column){
       return columnCount(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnCount(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, getRuntimeContext().getColumnFunctionFactory().createCountFunction(false));
   }
   default SqlColumnAsSelector<T1,TR> columnCountDistinct(Property<T1,?> column){
       return columnCountDistinct(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnCountDistinct(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, getRuntimeContext().getColumnFunctionFactory().createCountFunction(true));
   }
   default SqlColumnAsSelector<T1,TR> columnSum(Property<T1,Number> column){
       return columnSum(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnSum(Property<T1,Number> column, Property<TR,Number> alias){
       return columnFunc(column, alias, getRuntimeContext().getColumnFunctionFactory().createSumFunction(false));
   }
   default SqlColumnAsSelector<T1,TR> columnSumDistinct(Property<T1,Number> column){
       return columnSumDistinct(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnSumDistinct(Property<T1,Number> column, Property<TR,Number> alias){
       return columnFunc(column, alias, getRuntimeContext().getColumnFunctionFactory().createSumFunction(true));
   }
    default SqlColumnAsSelector<T1,TR> columnMax(Property<T1,?> column){
        return columnMax(column,null);
    }
   default SqlColumnAsSelector<T1,TR> columnMax(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, getRuntimeContext().getColumnFunctionFactory().createMaxFunction());
   }
    default SqlColumnAsSelector<T1,TR> columnMin(Property<T1,?> column){
        return columnMin(column,null);
    }
   default SqlColumnAsSelector<T1,TR> columnMin(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, getRuntimeContext().getColumnFunctionFactory().createMinFunction());
   }
   default SqlColumnAsSelector<T1,TR> columnAvg(Property<T1,Number> column){
       return columnAvg(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnAvg(Property<T1,Number> column, Property<TR,Number> alias){
       return columnFunc(column, alias, getRuntimeContext().getColumnFunctionFactory().createAvgFunction(false));
   }
   default SqlColumnAsSelector<T1,TR> columnAvgDistinct(Property<T1,Number> column){
       return columnAvgDistinct(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnAvgDistinct(Property<T1,Number> column, Property<TR,Number> alias){
       return columnFunc(column, alias, getRuntimeContext().getColumnFunctionFactory().createAvgFunction(true));
   }
   default SqlColumnAsSelector<T1,TR> columnLen(Property<T1,?> column){
       return columnLen(column,null);
   }
   default SqlColumnAsSelector<T1,TR> columnLen(Property<T1,?> column, Property<TR,?> alias){
       return columnFunc(column, alias, getRuntimeContext().getColumnFunctionFactory().createLenFunction());
   }

    SqlColumnAsSelector<T1,TR> columnFunc(Property<T1, ?> column, Property<TR, ?> alias, ColumnFunction columnFunction);//EasyAggregate
   default  <T2> SqlColumnAsSelector<T2,TR> then(SqlColumnAsSelector<T2,TR> sub){
       return sub;
   }
}
