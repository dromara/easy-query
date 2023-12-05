package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.util.Collection;

/**
 * create time 2023/9/25 16:35
 * 集合判断
 *
 * @author xuejiaming
 */
public interface ProxyValuesPredicate<TChain> extends FilterAvailable, ChainCast<TChain> {

    /**
     * column in collection
     * 集合为空返回False
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain in(SQLColumn<TProxy, TProperty> column, Collection<TProperty> collection) {
        return in(true, column, collection);
    }

    /**
     * column in collection
     * 集合为空返回False
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain in(boolean condition, SQLColumn<TProxy, TProperty> column, Collection<TProperty> collection) {
        if (condition) {
            getFilter().in(column.getTable(), column.getValue(), collection);
        }
        return castChain();
    }

    /**
     * column in collection
     * 数组为空返回False
     *
     * @param column
     * @param collection
     * @param <TProperty>
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain in(SQLColumn<TProxy, TProperty> column, TProperty[] collection) {
        return in(true, column, collection);
    }

    /**
     * column in collection
     * 数组为空返回False
     *
     * @param condition
     * @param column
     * @param collection
     * @param <TProperty>
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain in(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty[] collection) {
        if (condition) {
            getFilter().in(column.getTable(), column.getValue(), collection);
        }
        return castChain();
    }


    /**
     * column not in collection
     * 集合为空返回True
     *
     * @param column
     * @param collection
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notIn(SQLColumn<TProxy, TProperty> column, Collection<TProperty> collection) {
        return notIn(true, column, collection);
    }


    /**
     * column not in collection
     * 集合为空返回True
     *
     * @param condition
     * @param column
     * @param collection
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notIn(boolean condition, SQLColumn<TProxy, TProperty> column, Collection<TProperty> collection) {
        if (condition) {
            getFilter().notIn(column.getTable(), column.getValue(), collection);
        }
        return castChain();
    }

    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notIn(SQLColumn<TProxy, TProperty> column, TProperty[] collection) {
        return notIn(true, column, collection);
    }

    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain notIn(boolean condition, SQLColumn<TProxy, TProperty> column, TProperty[] collection) {
        if (condition) {
            getFilter().notIn(column.getTable(), column.getValue(), collection);
        }
        return castChain();
    }

}
