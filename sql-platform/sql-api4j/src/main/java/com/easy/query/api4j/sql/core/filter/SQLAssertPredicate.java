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
}
