package com.easy.query.api4j.func;

import com.easy.query.api4j.func.column.SQLColumnFuncSelectorImpl;
import com.easy.query.api4j.func.column.SQLColumnFuncSelector;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.func.SQLFuncAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;

/**
 * create time 2023/10/18 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface LambdaAggregateSQLFunc<T1> extends SQLFuncAvailable {
    /**
     * 求和函数
     * @param property 属性列
     * @return 求和函数
     */
    default DistinctDefaultSQLFunction sum(Property<T1, ?> property){
        return getSQLFunc().sum(EasyLambdaUtil.getPropertyName(property));
    }
    /**
     * 求和函数
     * @param sqlFunction sum函数
     * @return 求和函数
     */
    default DistinctDefaultSQLFunction sum(SQLFunction sqlFunction){
        return getSQLFunc().sum(sqlFunction);
    }

    /**
     * 求和函数
     * @param sqlExpression 属性选择函数
     * @return 求和函数
     */
    default DistinctDefaultSQLFunction sum(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().sum(o->{
            SQLColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }

    /**
     * 数量统计函数
     * @param property 属性列
     * @return 数量统计函数
     */
    default DistinctDefaultSQLFunction count(Property<T1, ?> property){
        return getSQLFunc().count(EasyLambdaUtil.getPropertyName(property));
    }
    /**
     * 数量统计函数
     * @param sqlFunction sum函数
     * @return 数量统计函数
     */
    default DistinctDefaultSQLFunction count(SQLFunction sqlFunction){
        return getSQLFunc().count(sqlFunction);
    }

    /**
     * 数量统计函数
     * @param sqlExpression 属性选择函数
     * @return 数量统计函数
     */
    default DistinctDefaultSQLFunction count(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().count(o->{
            SQLColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }



    /**
     * 最大值函数
     * @param property 属性列
     * @return 最大值函数
     */
    default SQLFunction max(Property<T1, ?> property){
        return getSQLFunc().max(EasyLambdaUtil.getPropertyName(property));
    }
    /**
     * 最大值函数
     * @param sqlFunction sum函数
     * @return 最大值函数
     */
    default SQLFunction max(SQLFunction sqlFunction){
        return getSQLFunc().max(sqlFunction);
    }

    /**
     * 最大值函数
     * @param sqlExpression 属性选择函数
     * @return 最大值函数
     */
    default SQLFunction max(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().max(o->{
            SQLColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }


    /**
     * 最小值函数
     * @param property 属性列
     * @return 最小值函数
     */
    default SQLFunction min(Property<T1, ?> property){
        return getSQLFunc().min(EasyLambdaUtil.getPropertyName(property));
    }
    /**
     * 最小值函数
     * @param sqlFunction sum函数
     * @return 最小值函数
     */
    default SQLFunction min(SQLFunction sqlFunction){
        return getSQLFunc().min(sqlFunction);
    }

    /**
     * 最小值函数
     * @param sqlExpression 属性选择函数
     * @return 最小值函数
     */
    default SQLFunction min(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().min(o->{
            SQLColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }



    /**
     * 平均值函数
     * @param property 属性列
     * @return 平均值函数
     */
    default DistinctDefaultSQLFunction avg(Property<T1, ?> property){
        return getSQLFunc().avg(EasyLambdaUtil.getPropertyName(property));
    }
    /**
     * 平均值函数
     * @param sqlFunction sum函数
     * @return 平均值函数
     */
    default DistinctDefaultSQLFunction avg(SQLFunction sqlFunction){
        return getSQLFunc().avg(sqlFunction);
    }

    /**
     * 平均值函数
     * @param sqlExpression 属性选择函数
     * @return 平均值函数
     */
    default DistinctDefaultSQLFunction avg(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().avg(o->{
            SQLColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }
}
