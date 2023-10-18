package com.easy.query.api4kt.func;

import com.easy.query.api4kt.func.column.SQLKtColumnFuncSelectorImpl;
import com.easy.query.api4kt.func.column.SQLKtColumnFuncSelector;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.func.SQLFuncAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/10/18 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface KtLambdaAggregateSQLFunc<T1> extends SQLFuncAvailable {
    /**
     * 求和函数
     * @param property 属性列
     * @return 求和函数
     */
    default DistinctDefaultSQLFunction sum(KProperty1<? super T1, ?> property){
        return getSQLFunc().sum(EasyKtLambdaUtil.getPropertyName(property));
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
    default DistinctDefaultSQLFunction sum(SQLExpression1<SQLKtColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().sum(o->{
            SQLKtColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLKtColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }

    /**
     * 数量统计函数
     * @param property 属性列
     * @return 数量统计函数
     */
    default DistinctDefaultSQLFunction count(KProperty1<? super T1, ?> property){
        return getSQLFunc().count(EasyKtLambdaUtil.getPropertyName(property));
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
    default DistinctDefaultSQLFunction count(SQLExpression1<SQLKtColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().count(o->{
            SQLKtColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLKtColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }



    /**
     * 最大值函数
     * @param property 属性列
     * @return 最大值函数
     */
    default SQLFunction max(KProperty1<? super T1, ?> property){
        return getSQLFunc().max(EasyKtLambdaUtil.getPropertyName(property));
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
    default SQLFunction max(SQLExpression1<SQLKtColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().max(o->{
            SQLKtColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLKtColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }


    /**
     * 最小值函数
     * @param property 属性列
     * @return 最小值函数
     */
    default SQLFunction min(KProperty1<? super T1, ?> property){
        return getSQLFunc().min(EasyKtLambdaUtil.getPropertyName(property));
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
    default SQLFunction min(SQLExpression1<SQLKtColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().min(o->{
            SQLKtColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLKtColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }



    /**
     * 平均值函数
     * @param property 属性列
     * @return 平均值函数
     */
    default DistinctDefaultSQLFunction avg(KProperty1<? super T1, ?> property){
        return getSQLFunc().avg(EasyKtLambdaUtil.getPropertyName(property));
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
    default DistinctDefaultSQLFunction avg(SQLExpression1<SQLKtColumnFuncSelector<T1>> sqlExpression){
        return getSQLFunc().avg(o->{
            SQLKtColumnFuncSelectorImpl<T1> sqlColumnConcatSelector = new SQLKtColumnFuncSelectorImpl<>(o);
            sqlExpression.apply(sqlColumnConcatSelector);
        });
    }
}
