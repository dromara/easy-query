package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/10/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyFuncAvailableValuePredicate<TChain> extends FilterAvailable, ChainCast<TChain> {

    /**
     * 大于 func(column) > val
     */
    default TChain gt(DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val) {
        return gt(true, dslsqlFunctionAvailable, val);
    }

    /**
     * 大于 func(column) > val
     *
     * @param condition 执行条件
     * @param dslsqlFunctionAvailable 函数
     * @param val       值
     * @return children
     */
    default TChain gt(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val) {

        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().gt(dslsqlFunctionAvailable.getTable(),sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 等于 func(column) >= val
     */
    default TChain ge(DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val) {
        return ge(true, dslsqlFunctionAvailable, val);
    }

    /**
     * 等于 func(column) >= val
     *
     * @param condition 执行条件
     * @param dslsqlFunctionAvailable 函数
     * @param val       值
     * @return children
     */
    default TChain ge(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val){

        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().ge(dslsqlFunctionAvailable.getTable(),sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 等于 func(column) = val
     */
    default TChain eq(DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val) {
        return eq(true, dslsqlFunctionAvailable, val);
    }

    /**
     * 等于 func(column) = val
     *
     * @param condition 执行条件
     * @param dslsqlFunctionAvailable 函数
     * @param val 值
     * @return children
     */
    default TChain eq(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val) {

        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().eq(dslsqlFunctionAvailable.getTable(),sqlFunction, val);
        }
        return castChain();
    }


    /**
     * 不等于 func(column) <> val
     */
    default TChain ne(DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val) {
        return ne(true, dslsqlFunctionAvailable, val);
    }

    /**
     * 不等于 func(column) <> val
     *
     * @param condition 执行条件
     * @param dslsqlFunctionAvailable 函数
     * @param val       值
     * @return children
     */
    default TChain ne(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val){
        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().ne(dslsqlFunctionAvailable.getTable(),sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 小于等于 func(column) <= val
     */
    default TChain le(DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val) {
        return le(true, dslsqlFunctionAvailable, val);
    }

    /**
     * 小于等于 func(column) <= val
     *
     * @param condition 执行条件
     * @param dslsqlFunctionAvailable 函数
     * @param val       值
     * @return children
     */
    default TChain le(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val){
        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().le(dslsqlFunctionAvailable.getTable(),sqlFunction, val);
        }
        return castChain();
    }

    /**
     * 小于 func(column) < val
     */
    default TChain lt(DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val) {
        return lt(true, dslsqlFunctionAvailable, val);
    }

    /**
     * 小于 func(column) < val
     *
     * @param condition 执行条件
     * @param dslsqlFunctionAvailable 字段
     * @param val       值
     * @return children
     */
    default TChain lt(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, Object val){
        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().lt(dslsqlFunctionAvailable.getTable(),sqlFunction, val);
        }
        return castChain();
    }
}
