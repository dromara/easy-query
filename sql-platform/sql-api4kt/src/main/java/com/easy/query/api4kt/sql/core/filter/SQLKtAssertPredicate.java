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
    default <TProperty> TChain isNull(KProperty1<? super T1, TProperty> column) {
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
    default <TProperty> TChain isNull(boolean condition, KProperty1<? super T1, TProperty> column) {
        getWherePredicate().isNull(condition, EasyKtLambdaUtil.getPropertyName(column));
        return castChain();
    }

    /**
     * column is not null
     */
    default <TProperty> TChain isNotNull(KProperty1<? super T1, TProperty> column) {
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
    default <TProperty> TChain isNotNull(boolean condition, KProperty1<? super T1, TProperty> column) {
        getWherePredicate().isNotNull(condition, EasyKtLambdaUtil.getPropertyName(column));
        return castChain();
    }
    /**
     * column is null or empty
     */
    default TChain isBlank(KProperty1<? super T1, String> column) {
        return isBlank(true, column);
    }

    /**
     * column is null or empty
     *
     * @param condition 执行条件
     * @param column  字段
     * @return children
     */
    TChain isBlank(boolean condition, KProperty1<? super T1, String> column);

    /**
     * column is not null and not empty
     */
    default TChain isNotBlank(KProperty1<? super T1, String> column) {
        return isNotBlank(true, column);
    }

    /**
     * column is not null and not empty
     *
     * @param condition 执行条件
     * @param column  字段
     * @return children
     */
    TChain isNotBlank(boolean condition, KProperty1<? super T1, String> column);
    /**
     * column is null or empty
     */
    default TChain isEmpty(KProperty1<? super T1, String> column) {
        return isEmpty(true, column);
    }

    /**
     * column is null or empty
     *
     * @param condition 执行条件
     * @param column  字段
     * @return children
     */
    TChain isEmpty(boolean condition, KProperty1<? super T1, String> column);

    /**
     * column is not null and not empty
     */
    default TChain isNotEmpty(KProperty1<? super T1, String> column) {
        return isNotEmpty(true, column);
    }

    /**
     * column is not null and not empty
     *
     * @param condition 执行条件
     * @param column  字段
     * @return children
     */
    TChain isNotEmpty(boolean condition, KProperty1<? super T1, String> column);
}
