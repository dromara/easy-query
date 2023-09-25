package com.easy.query.api4kt.sql.core.filter;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/9/25 17:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtAssertPredicate<T1, TChain> extends EntitySQLTableOwner<T1>, SQLKtWherePredicateAvailable<T1>, ChainCast<TChain> {

    /**
     * column is null
     */
    default <TProperty> TChain isNull(KProperty1<T1, TProperty> column) {
        getWherePredicate().isNull(EasyKtLambdaUtil.getPropertyName(column));
        return castChain();
    }

    /**
     * column is null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default <TProperty> TChain isNull(boolean condition, KProperty1<T1, TProperty> column) {
        getWherePredicate().isNull(condition, EasyKtLambdaUtil.getPropertyName(column));
        return castChain();
    }

    /**
     * column is not null
     */
    default <TProperty> TChain isNotNull(KProperty1<T1, TProperty> column) {
        getWherePredicate().isNotNull(EasyKtLambdaUtil.getPropertyName(column));
        return castChain();
    }

    /**
     * column is not null
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default <TProperty> TChain isNotNull(boolean condition, KProperty1<T1, TProperty> column) {
        getWherePredicate().isNotNull(condition, EasyKtLambdaUtil.getPropertyName(column));
        return castChain();
    }
}
