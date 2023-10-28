package com.easy.query.api4j.sql.core.filter;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;

/**
 * create time 2023/9/25 17:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAssertPredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLWherePredicateAvailable<T1>, ChainCast<TChain> {

    /**
     * column is null
     */
    default <TProperty> TChain isNull(Property<T1, TProperty> column) {
        getWherePredicate().isNull(EasyLambdaUtil.getPropertyName(column));
        return castChain();
    }

    /**
     * column is null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default <TProperty> TChain isNull(boolean condition, Property<T1, TProperty> column) {
        getWherePredicate().isNull(condition, EasyLambdaUtil.getPropertyName(column));
        return castChain();
    }

    /**
     * column is not null
     */
    default <TProperty> TChain isNotNull(Property<T1, TProperty> column) {
        getWherePredicate().isNotNull(EasyLambdaUtil.getPropertyName(column));
        return castChain();
    }

    /**
     * column is not null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default <TProperty> TChain isNotNull(boolean condition, Property<T1, TProperty> column) {
        getWherePredicate().isNotNull(condition, EasyLambdaUtil.getPropertyName(column));
        return castChain();
    }
    /**
     * column is null or empty
     */
    default TChain isEmpty(Property<T1, String> column) {
        return isEmpty(true, column);
    }

    /**
     * column is null or empty
     *
     * @param condition 执行条件
     * @param column  字段
     * @return children
     */
    TChain isEmpty(boolean condition, Property<T1, String> column);

    /**
     * column is not null and not empty
     */
    default TChain isNotEmpty(Property<T1, String> column) {
        return isNotEmpty(true, column);
    }

    /**
     * column is not null and not empty
     *
     * @param condition 执行条件
     * @param column  字段
     * @return children
     */
    TChain isNotEmpty(boolean condition, Property<T1, String> column);
    /**
     * column is null or empty
     */
    default TChain isBank(Property<T1, String> column) {
        return isBank(true, column);
    }

    /**
     * column is null or empty
     *
     * @param condition 执行条件
     * @param column  字段
     * @return children
     */
    TChain isBank(boolean condition, Property<T1, String> column);

    /**
     * column is not null and not empty
     */
    default TChain isNotBank(Property<T1, String> column) {
        return isNotBank(true, column);
    }

    /**
     * column is not null and not empty
     *
     * @param condition 执行条件
     * @param column  字段
     * @return children
     */
    TChain isNotBank(boolean condition, Property<T1, String> column);
}
