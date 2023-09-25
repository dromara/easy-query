package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/9/25 16:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyValuePredicate<TChain> extends FilterAvailable, ChainCast<TChain> {


    /**
     * 大于 column > val
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain gt(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return gt(true, column, val);
    }

    /**
     * 大于 column > val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain gt(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        if (condition) {
            getFilter().gt(column.getTable(), column.value(), val);
        }
        return castChain();
    }

    /**
     * 等于 column >= val
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ge(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return ge(true, column, val);
    }

    /**
     * 等于 column >= val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ge(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        if (condition) {
            getFilter().ge(column.getTable(), column.value(), val);
        }
        return castChain();
    }

    /**
     * 等于 column = val
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain eq(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return eq(true, column, val);
    }

    /**
     * 等于 column = val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain eq(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        if (condition) {
            getFilter().eq(column.getTable(), column.value(), val);
        }
        return castChain();
    }

    /**
     * 不等于 column <> val
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ne(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return ne(true, column, val);
    }

    /**
     * 不等于 column <> val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ne(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        if (condition) {
            getFilter().ne(column.getTable(), column.value(), val);
        }
        return castChain();
    }

    /**
     * 小于等于 column <= val
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain le(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return le(true, column, val);
    }

    /**
     * 小于等于 column <= val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain le(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        if (condition) {
            getFilter().le(column.getTable(), column.value(), val);
        }
        return castChain();
    }

    /**
     * 小于 column < val
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain lt(SQLColumn<TProxy, TProperty> column, TProperty val) {
        return lt(column, val);
    }

    /**
     * 小于 column < val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain lt(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty val) {
        if (condition) {
            getFilter().lt(column.getTable(), column.value(), val);
        }
        return castChain();
    }


}
