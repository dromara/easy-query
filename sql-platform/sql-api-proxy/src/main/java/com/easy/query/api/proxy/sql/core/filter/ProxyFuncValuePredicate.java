package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
@Deprecated
public interface ProxyFuncValuePredicate<TChain> extends FilterAvailable, ChainCast<TChain> {

    /**
     * 大于 func(column) > val
     */
    @Deprecated
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
    @Deprecated
    default TChain gt(boolean condition, SQLFunction sqlFunction, Object val) {

        if (condition) {
            getFilter().gt(null,sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 等于 func(column) >= val
     */
    @Deprecated
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
    @Deprecated
    default TChain ge(boolean condition, SQLFunction sqlFunction, Object val){

        if (condition) {
            getFilter().ge(null,sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 等于 func(column) = val
     */
    @Deprecated
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
    @Deprecated
    default TChain eq(boolean condition, SQLFunction sqlFunction, Object val) {

        if (condition) {
            getFilter().eq(null,sqlFunction, val);
        }
        return castChain();
    }


    /**
     * 不等于 func(column) <> val
     */
    @Deprecated
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
    @Deprecated
    default TChain ne(boolean condition, SQLFunction sqlFunction, Object val){
        if (condition) {
            getFilter().ne(null,sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 小于等于 func(column) <= val
     */
    @Deprecated
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
    @Deprecated
    default TChain le(boolean condition, SQLFunction sqlFunction, Object val){
        if (condition) {
            getFilter().le(null,sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 小于 func(column) < val
     */
    @Deprecated
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
    @Deprecated
    default TChain lt(boolean condition, SQLFunction sqlFunction, Object val){
        if (condition) {
            getFilter().lt(null,sqlFunction, val);
        }
        return castChain();
    }
}
