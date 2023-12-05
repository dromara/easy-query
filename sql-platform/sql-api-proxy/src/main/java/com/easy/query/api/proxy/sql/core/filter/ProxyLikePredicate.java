package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/9/25 17:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyLikePredicate<TChain> extends FilterAvailable, ChainCast<TChain> {


    /**
     * column like val%
     * 列匹配前半部分
     *
     * @param column
     * @param val
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain likeMatchLeft(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return likeMatchLeft(true, column, val);
    }

    /**
     * column like val%
     * 列匹配前半部分
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain likeMatchLeft(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        return like(condition, column, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column like %val
     * 列匹配后半部分
     *
     * @param column
     * @param val
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain likeMatchRight(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return likeMatchRight(true, column, val);
    }

    /**
     * column like %val
     * 列匹配后半部分
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain likeMatchRight(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        return like(condition, column, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain like(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return like(true, column, val);
    }

    /**
     * column like %val%
     * 列全匹配
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain like(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        return like(condition, column, val, SQLLikeEnum.LIKE_PERCENT_ALL);
    }

    /**
     * column like ?val?
     * 列自定义匹配
     *
     * @param condition
     * @param column
     * @param val
     * @param sqlLike
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain like(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val, SQLLikeEnum sqlLike) {
        if (condition) {
            getFilter().like(column.getTable(), column.getValue(), val, sqlLike);
        }
        return castChain();
    }

    /**
     * column not like val%
     *
     * @param column
     * @param val
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notLikeMatchLeft(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return notLikeMatchLeft(true, column, val);
    }

    /**
     * column not like val%
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notLikeMatchLeft(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        return notLike(condition, column, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column not like %val
     *
     * @param column
     * @param val
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notLikeMatchRight(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return notLikeMatchRight(true, column, val);
    }

    /**
     * column not like %val
     *
     * @param condition
     * @param column
     * @param val
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notLikeMatchRight(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        return notLike(condition, column, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column not like %val%
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notLike(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return notLike(true, column, val);
    }

    /**
     * column not like %val%
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notLike(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        return notLike(condition, column, val, SQLLikeEnum.LIKE_PERCENT_ALL);
    }

    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notLike(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val, SQLLikeEnum sqlLike) {
        if (condition) {
            getFilter().notLike(column.getTable(), column.getValue(), val, sqlLike);
        }
        return castChain();
    }
}
