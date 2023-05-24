package com.easy.query.core.expression.parser.core;

import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.func.DefaultColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @Description: 文件说明
 * @Date: 2023/2/6 22:58
 * @author xuejiaming
 */
public interface SQLColumnAsSelector<T1,TR>  {
    QueryRuntimeContext getRuntimeContext();
    TableAvailable getTable();
    SQLColumnAsSelector<T1,TR> column(Property<T1,?> column);
    SQLColumnAsSelector<T1,TR> columnIgnore(Property<T1,?> column);

    /**
     * 映射到TR的所有列上,按ColumnName进行映射,如果TR上没有对应的列名那么将不会映射查询列
     * @return
     */
    SQLColumnAsSelector<T1,TR> columnAll();
    SQLColumnAsSelector<T1,TR> columnAs(Property<T1,?> column, Property<TR, ?> alias);
   <TSubQuery> SQLColumnAsSelector<T1,TR> columnSubQueryAs(Function<SQLWherePredicate<T1>,Queryable<TSubQuery>> subQueryableFunc, Property<TR, TSubQuery> alias);
   default SQLColumnAsSelector<T1,TR> columnCount(Property<T1,?> column){
       return columnCountAs(column,null);
   }

    /**
     * 请使用{@link #columnCountAs}接口来设置别名后续将会废弃
     * @param column
     * @param alias
     * @return
     */
   @Deprecated
   default SQLColumnAsSelector<T1,TR> columnCount(Property<T1,?> column, Property<TR,?> alias){
       return columnCountAs(column,alias);
   }
   default SQLColumnAsSelector<T1,TR> columnCountAs(Property<T1,?> column, Property<TR,?> alias){
       ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createCountFunction(false);
       ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(column, countFunction);
       return columnFuncAs(columnPropertyFunction, alias);
   }
   default SQLColumnAsSelector<T1,TR> columnCountDistinct(Property<T1,?> column){
       return columnCountDistinctAs(column,null);
   }
    /**
     * 请使用{@link #columnCountDistinctAs}接口来设置别名后续将会废弃
     * @param column
     * @param alias
     * @return
     */
    @Deprecated
   default SQLColumnAsSelector<T1,TR> columnCountDistinct(Property<T1,?> column, Property<TR,?> alias){
       return columnCountDistinctAs(column,alias);
   }
   default SQLColumnAsSelector<T1,TR> columnCountDistinctAs(Property<T1,?> column, Property<TR,?> alias){
       ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createCountFunction(true);
       ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(column, countFunction);
       return columnFuncAs(columnPropertyFunction, alias);
   }
   default SQLColumnAsSelector<T1,TR> columnSum(Property<T1,Number> column){
       return columnSumAs(column,null);
   }
    /**
     * 请使用{@link #columnSumAs}接口来设置别名后续将会废弃
     * @param column
     * @param alias
     * @return
     */
    @Deprecated
   default SQLColumnAsSelector<T1,TR> columnSum(Property<T1,Number> column, Property<TR,Number> alias){
       return columnSumAs(column,alias);
   }
   default SQLColumnAsSelector<T1,TR> columnSumAs(Property<T1,Number> column, Property<TR,Number> alias){
       ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createSumFunction(false);
       ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(column, countFunction);
       return columnFuncAs(columnPropertyFunction, alias);
   }
   default SQLColumnAsSelector<T1,TR> columnSumDistinct(Property<T1,Number> column){
       return columnSumDistinctAs(column,null);
   }
    /**
     * 请使用{@link #columnSumDistinctAs}接口来设置别名后续将会废弃
     * @param column
     * @param alias
     * @return
     */
    @Deprecated
   default SQLColumnAsSelector<T1,TR> columnSumDistinct(Property<T1,Number> column, Property<TR,Number> alias){
       return columnSumDistinctAs(column,alias);
   }
   default SQLColumnAsSelector<T1,TR> columnSumDistinctAs(Property<T1,Number> column, Property<TR,Number> alias){
       ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createSumFunction(true);
       ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(column, countFunction);
       return columnFuncAs(columnPropertyFunction, alias);
   }
    default SQLColumnAsSelector<T1,TR> columnMax(Property<T1,?> column){
        return columnMaxAs(column,null);
    }

    /**
     * 请使用{@link #columnMaxAs}接口来设置别名后续将会废弃
     * @param column
     * @param alias
     * @return
     */
    @Deprecated
    default SQLColumnAsSelector<T1,TR> columnMax(Property<T1,?> column, Property<TR,?> alias){
        return columnMaxAs(column,alias);
    }
   default SQLColumnAsSelector<T1,TR> columnMaxAs(Property<T1,?> column, Property<TR,?> alias){
       ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createMaxFunction();
       ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(column, countFunction);
       return columnFuncAs(columnPropertyFunction, alias);
   }
    default SQLColumnAsSelector<T1,TR> columnMin(Property<T1,?> column){
        return columnMinAs(column,null);
    }

    /**
     * 请使用{@link #columnMaxAs}接口来设置别名后续将会废弃
     * @param column
     * @param alias
     * @return
     */
    @Deprecated
    default SQLColumnAsSelector<T1,TR> columnMin(Property<T1,?> column, Property<TR,?> alias){
        return columnMinAs(column,alias);
    }
   default SQLColumnAsSelector<T1,TR> columnMinAs(Property<T1,?> column, Property<TR,?> alias){
       ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createMinFunction();
       ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(column, countFunction);
       return columnFuncAs(columnPropertyFunction, alias);
   }
   default SQLColumnAsSelector<T1,TR> columnAvg(Property<T1,Number> column){
       return columnAvgAs(column,null);
   }

    /**
     * 请使用{@link #columnAvgAs}接口来设置别名后续将会废弃
     * @param column
     * @param alias
     * @return
     */
    @Deprecated
   default SQLColumnAsSelector<T1,TR> columnAvg(Property<T1,Number> column, Property<TR,Number> alias){
       return columnAvgAs(column,alias);
   }
   default SQLColumnAsSelector<T1,TR> columnAvgAs(Property<T1,Number> column, Property<TR,Number> alias){
       ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createAvgFunction(false);
       ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(column, countFunction);
       return columnFuncAs(columnPropertyFunction, alias);
   }
   default SQLColumnAsSelector<T1,TR> columnAvgDistinct(Property<T1,Number> column){
       return columnAvgDistinctAs(column,null);
   }

    /**
     * 请使用{@link #columnAvgDistinctAs}接口来设置别名后续将会废弃
     * @param column
     * @param alias
     * @return
     */
    @Deprecated
   default SQLColumnAsSelector<T1,TR> columnAvgDistinct(Property<T1,Number> column, Property<TR,Number> alias){
       return columnAvgDistinctAs(column,alias);
   }
   default SQLColumnAsSelector<T1,TR> columnAvgDistinctAs(Property<T1,Number> column, Property<TR,Number> alias){
       ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createAvgFunction(true);
       ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(column, countFunction);
       return columnFuncAs(columnPropertyFunction, alias);
   }
   default SQLColumnAsSelector<T1,TR> columnLen(Property<T1,?> column){
       return columnLenAs(column,null);
   }
    /**
     * 请使用{@link #columnLenAs}接口来设置别名后续将会废弃
     * @param column
     * @param alias
     * @return
     */
    @Deprecated
   default SQLColumnAsSelector<T1,TR> columnLen(Property<T1,?> column, Property<TR,?> alias){
       return columnLenAs(column,alias);
   }
   default SQLColumnAsSelector<T1,TR> columnLenAs(Property<T1,?> column, Property<TR,?> alias){
       ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createLenFunction();
       ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(column, countFunction);
       return columnFuncAs(columnPropertyFunction, alias);
   }

    SQLColumnAsSelector<T1,TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, Property<TR, ?> alias);
   default  <T2> SQLColumnAsSelector<T2,TR> then(SQLColumnAsSelector<T2,TR> sub){
       return sub;
   }
}
