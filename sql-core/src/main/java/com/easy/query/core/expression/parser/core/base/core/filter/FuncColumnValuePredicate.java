package com.easy.query.core.expression.parser.core.base.core.filter;

import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface FuncColumnValuePredicate<T1, TChain> extends EntitySQLTableOwner<T1>, FilterAvailable, ChainCast<TChain> {

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
            getFilter().gt(getTable(), sqlFunction, val);
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
            getFilter().ge(getTable(), sqlFunction, val);
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
            getFilter().eq(getTable(), sqlFunction, val);
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
            getFilter().ne(getTable(), sqlFunction, val);
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
            getFilter().le(getTable(), sqlFunction, val);
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
            getFilter().lt(getTable(), sqlFunction, val);
        }
        return castChain();
    }
}
