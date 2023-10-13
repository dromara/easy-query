package com.easy.query.api4j.sql.core.filter;

import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFuncValuePredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLWherePredicateAvailable<T1>, ChainCast<TChain> {

    /**
     * 大于 func(column) > val
     */
    default TChain gt(SQLFunction sqlFunction, Object val) {
        return gt(true, sqlFunction, val);
    }

    /**
     * 大于 func(column) > val
     *
     * @param condition 执行条件
     * @param sqlFunction 函数
     * @param val       值
     * @return children
     */
    default TChain gt(boolean condition, SQLFunction sqlFunction, Object val) {

        if (condition) {
            getWherePredicate().gt(sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 等于 func(column) >= val
     */
    default TChain ge(SQLFunction sqlFunction, Object val) {
        return ge(true, sqlFunction, val);
    }

    /**
     * 等于 func(column) >= val
     *
     * @param condition 执行条件
     * @param sqlFunction 函数
     * @param val       值
     * @return children
     */
    default TChain ge(boolean condition, SQLFunction sqlFunction, Object val){

        if (condition) {
            getWherePredicate().ge(sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 等于 func(column) = val
     */
    default TChain eq(SQLFunction sqlFunction, Object val) {
        return eq(true, sqlFunction, val);
    }

    /**
     * 等于 func(column) = val
     *
     * @param condition 执行条件
     * @param sqlFunction 函数
     * @param val 值
     * @return children
     */
    default TChain eq(boolean condition, SQLFunction sqlFunction, Object val) {

        if (condition) {
            getWherePredicate().eq(sqlFunction, val);
        }
        return castChain();
    }


    /**
     * 不等于 func(column) <> val
     */
    default TChain ne(SQLFunction sqlFunction, Object val) {
        return ne(true, sqlFunction, val);
    }

    /**
     * 不等于 func(column) <> val
     *
     * @param condition 执行条件
     * @param sqlFunction 函数
     * @param val       值
     * @return children
     */
    default TChain ne(boolean condition, SQLFunction sqlFunction, Object val){
        if (condition) {
            getWherePredicate().ne(sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 小于等于 func(column) <= val
     */
    default TChain le(SQLFunction sqlFunction, Object val) {
        return le(true, sqlFunction, val);
    }

    /**
     * 小于等于 func(column) <= val
     *
     * @param condition 执行条件
     * @param sqlFunction 函数
     * @param val       值
     * @return children
     */
    default TChain le(boolean condition, SQLFunction sqlFunction, Object val){
        if (condition) {
            getWherePredicate().le(sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 小于 func(column) < val
     */
    default TChain lt(SQLFunction sqlFunction, Object val) {
        return lt(true, sqlFunction, val);
    }

    /**
     * 小于 func(column) < val
     *
     * @param condition 执行条件
     * @param sqlFunction 字段
     * @param val       值
     * @return children
     */
    default TChain lt(boolean condition, SQLFunction sqlFunction, Object val){
        if (condition) {
            getWherePredicate().lt(sqlFunction, val);
        }
        return castChain();
    }
}
