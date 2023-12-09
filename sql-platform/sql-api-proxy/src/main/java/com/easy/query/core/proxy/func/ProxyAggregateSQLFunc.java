package com.easy.query.core.proxy.func;

import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.SimpleSQLTableOwner;
import com.easy.query.core.func.SQLFuncAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.DistinctDefaultSQLFunction;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/10/18 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyAggregateSQLFunc extends SQLFuncAvailable {
    /**
     * 求和函数
     * @param sqlColumn 属性列
     * @return 求和函数
     */
    default <TProxy, T> DistinctDefaultSQLFunction sum(SQLColumn<TProxy, T> sqlColumn){
        return getSQLFunc().sum(o->{
            o.column(new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
        });
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
    default DistinctDefaultSQLFunction sum(SQLExpression1<ProxyColumnFuncSelector> sqlExpression){
        return getSQLFunc().sum(o->{
            sqlExpression.apply(new ProxyColumnFuncSelectorImpl(o));
        });
    }

    /**
     * 数量统计函数
     * @param sqlColumn 属性列
     * @return 数量统计函数
     */
    default <TProxy, T> DistinctDefaultSQLFunction count(SQLColumn<TProxy, T> sqlColumn){
        return getSQLFunc().count(o->{
            o.column(new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
        });
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
    default DistinctDefaultSQLFunction count(SQLExpression1<ProxyColumnFuncSelector> sqlExpression){
        return getSQLFunc().count(o->{
            sqlExpression.apply(new ProxyColumnFuncSelectorImpl(o));
        });
    }



    /**
     * 最大值函数
     * @param sqlColumn 属性列
     * @return 最大值函数
     */
    default <TProxy, T> SQLFunction max(SQLColumn<TProxy, T> sqlColumn){
        return getSQLFunc().max(o->{
            o.column(new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
        });
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
    default SQLFunction max(SQLExpression1<ProxyColumnFuncSelector> sqlExpression){
        return getSQLFunc().max(o->{
            sqlExpression.apply(new ProxyColumnFuncSelectorImpl(o));
        });
    }


    /**
     * 最小值函数
     * @param sqlColumn 属性列
     * @return 最小值函数
     */
    default <TProxy, T> SQLFunction min(SQLColumn<TProxy, T> sqlColumn){
        return getSQLFunc().min(o->{
            o.column(new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
        });
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
    default SQLFunction min(SQLExpression1<ProxyColumnFuncSelector> sqlExpression){
        return getSQLFunc().min(o->{
            sqlExpression.apply(new ProxyColumnFuncSelectorImpl(o));
        });
    }



    /**
     * 平均值函数
     * @param sqlColumn 属性列
     * @return 平均值函数
     */
    default <TProxy, T> DistinctDefaultSQLFunction avg(SQLColumn<TProxy, T> sqlColumn){
        return getSQLFunc().avg(o->{
            o.column(new SimpleSQLTableOwner(sqlColumn.getTable()),sqlColumn.getValue());
        });
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
    default DistinctDefaultSQLFunction avg(SQLExpression1<ProxyColumnFuncSelector> sqlExpression){
        return getSQLFunc().avg(o->{
            sqlExpression.apply(new ProxyColumnFuncSelectorImpl(o));
        });
    }
}
