package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2023/9/25 17:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyFuncAvailableLikePredicate<TChain> extends FilterAvailable, ChainCast<TChain> {


    /**
     * column like val%
     * 列匹配前半部分
     *
     * @param dslsqlFunctionAvailable
     * @param val
     * @return
     */
    default <TProperty> TChain likeMatchLeft(DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return likeMatchLeft(true, dslsqlFunctionAvailable, val);
    }

    /**
     * column like val%
     * 列匹配前半部分
     *
     * @param condition
     * @param dslsqlFunctionAvailable
     * @param val
     * @return
     */
    default <TProperty> TChain likeMatchLeft(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return like(condition, dslsqlFunctionAvailable, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column like %val
     * 列匹配后半部分
     *
     * @param dslsqlFunctionAvailable
     * @param val
     * @return
     */
    default <TProperty> TChain likeMatchRight(DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return likeMatchRight(true, dslsqlFunctionAvailable, val);
    }

    /**
     * column like %val
     * 列匹配后半部分
     *
     * @param condition
     * @param dslsqlFunctionAvailable
     * @param val
     * @return
     */
    default <TProperty> TChain likeMatchRight(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return like(condition, dslsqlFunctionAvailable, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default <TProperty> TChain like(DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return like(true, dslsqlFunctionAvailable, val);
    }

    /**
     * column like %val%
     * 列全匹配
     *
     * @param condition 执行条件
     * @param dslsqlFunctionAvailable    字段
     * @param val       值
     * @return children
     */
    default <TProperty> TChain like(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return like(condition, dslsqlFunctionAvailable, val, SQLLikeEnum.LIKE_PERCENT_ALL);
    }

    /**
     * column like ?val?
     * 列自定义匹配
     *
     * @param condition
     * @param dslsqlFunctionAvailable
     * @param val
     * @param sqlLike
     * @return
     */
    default <TProperty> TChain like(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val, SQLLikeEnum sqlLike) {
        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().funcValueFilter(dslsqlFunctionAvailable.getTable(),sqlFunction,EasySQLUtil.getLikeParameter(val, sqlLike), SQLPredicateCompareEnum.LIKE);;
        }
        return castChain();
    }

    /**
     * column not like val%
     *
     * @param dslsqlFunctionAvailable
     * @param val
     * @return
     */
    default <TProperty> TChain notLikeMatchLeft(DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return notLikeMatchLeft(true, dslsqlFunctionAvailable, val);
    }

    /**
     * column not like val%
     *
     * @param condition
     * @param dslsqlFunctionAvailable
     * @param val
     * @return
     */
    default <TProperty> TChain notLikeMatchLeft(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return notLike(condition, dslsqlFunctionAvailable, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column not like %val
     *
     * @param dslsqlFunctionAvailable
     * @param val
     * @return
     */
    default <TProperty> TChain notLikeMatchRight(DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return notLikeMatchRight(true, dslsqlFunctionAvailable, val);
    }

    /**
     * column not like %val
     *
     * @param condition
     * @param dslsqlFunctionAvailable
     * @param val
     * @return
     */
    default <TProperty> TChain notLikeMatchRight(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return notLike(condition, dslsqlFunctionAvailable, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column not like %val%
     */
    default <TProperty> TChain notLike(DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return notLike(true, dslsqlFunctionAvailable, val);
    }

    /**
     * column not like %val%
     *
     * @param condition 执行条件
     * @param dslsqlFunctionAvailable    字段
     * @param val       值
     * @return children
     */
    default <TProperty> TChain notLike(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val) {
        return notLike(condition, dslsqlFunctionAvailable, val, SQLLikeEnum.LIKE_PERCENT_ALL);
    }

    default <TProperty> TChain notLike(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailable, TProperty val, SQLLikeEnum sqlLike) {
        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().funcValueFilter(dslsqlFunctionAvailable.getTable(),sqlFunction,EasySQLUtil.getLikeParameter(val, sqlLike), SQLPredicateCompareEnum.NOT_LIKE);;
        }
        return castChain();
    }
}
