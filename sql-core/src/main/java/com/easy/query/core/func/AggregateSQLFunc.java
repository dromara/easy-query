package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;

/**
 * create time 2023/10/18 14:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AggregateSQLFunc {
    /**
     * 求和函数
     * @param property 属性列
     * @return 求和函数
     */
    default DistinctDefaultSQLFunction sum(String property){
        return sum(s->{
            s.column(property);
        });
    }
    /**
     * 求和函数
     * @param sqlFunction sum函数
     * @return 求和函数
     */
    default DistinctDefaultSQLFunction sum(SQLFunction sqlFunction){
        return sum(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 求和函数
     * @param sqlExpression 属性选择函数
     * @return 求和函数
     */
    DistinctDefaultSQLFunction sum(SQLExpression1<ColumnFuncSelector> sqlExpression);
    /**
     * 数量统计函数
     * @param property 属性列
     * @return 数量统计函数
     */
    default DistinctDefaultSQLFunction count(String property){
        return count(s->{
            s.column(property);
        });
    }
    /**
     * 数量统计函数
     * @param sqlFunction sum函数
     * @return 数量统计函数
     */
    default DistinctDefaultSQLFunction count(SQLFunction sqlFunction){
        return count(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 数量统计函数
     * @param sqlExpression 属性选择函数
     * @return 数量统计函数
     */
    DistinctDefaultSQLFunction count(SQLExpression1<ColumnFuncSelector> sqlExpression);
    /**
     * 最大值函数
     * @param property 属性列
     * @return 最大值函数
     */
    default SQLFunction max(String property){
        return max(s->{
            s.column(property);
        });
    }
    /**
     * 最大值函数
     * @param sqlFunction sum函数
     * @return 最大值函数
     */
    default SQLFunction max(SQLFunction sqlFunction){
        return max(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 最大值函数
     * @param sqlExpression 属性选择函数
     * @return 最大值函数
     */
    SQLFunction max(SQLExpression1<ColumnFuncSelector> sqlExpression);
    /**
     * 最小值函数
     * @param property 属性列
     * @return 最小值函数
     */
    default SQLFunction min(String property){
        return min(s->{
            s.column(property);
        });
    }
    /**
     * 最小值函数
     * @param sqlFunction sum函数
     * @return 最小值函数
     */
    default SQLFunction min(SQLFunction sqlFunction){
        return min(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 最小值函数
     * @param sqlExpression 属性选择函数
     * @return 最小值函数
     */
    SQLFunction min(SQLExpression1<ColumnFuncSelector> sqlExpression);


    /**
     * 平均值函数
     * @param property 属性列
     * @return 平均值函数
     */
    default DistinctDefaultSQLFunction avg(String property){
        return avg(s->{
            s.column(property);
        });
    }
    /**
     * 平均值函数
     * @param sqlFunction sum函数
     * @return 平均值函数
     */
    default DistinctDefaultSQLFunction avg(SQLFunction sqlFunction){
        return avg(s->{
            s.sqlFunc(sqlFunction);
        });
    }

    /**
     * 平均值函数
     * @param sqlExpression 属性选择函数
     * @return 平均值函数
     */
    DistinctDefaultSQLFunction avg(SQLExpression1<ColumnFuncSelector> sqlExpression);
}
