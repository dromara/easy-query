package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/9/25 16:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySelfPredicate<TChain> extends FilterAvailable,ChainCast<TChain> {

    /**
     * 小于等于 column1 > column2
     *
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain gt(SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        return gt(true, column1, column2);
    }

    /**
     * 小于等于 column1 > column2
     *
     * @param condition
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain gt(boolean condition, SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        if (condition) {
            getFilter().gt(column1.getTable(), column1.getValue(), column2.getTable(), column2.getValue());
        }
        return castChain();
    }

    /**
     * 小于等于 column1 >= column2
     *
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain ge(SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        return ge(true, column1, column2);
    }

    /**
     * 小于等于 column1 >= column2
     *
     * @param condition
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain ge(boolean condition, SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        if (condition) {
            getFilter().ge(column1.getTable(), column1.getValue(), column2.getTable(), column2.getValue());
        }
        return castChain();
    }

    /**
     * 小于等于 column1 = column2
     *
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain eq(SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        return eq(true, column1, column2);
    }

    /**
     * 小于等于 column1 = column2
     *
     * @param condition
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain eq(boolean condition, SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        if (condition) {
            getFilter().eq(column1.getTable(), column1.getValue(), column2.getTable(), column2.getValue());
        }
        return castChain();
    }

    /**
     * 小于等于 column1 <> column2
     *
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain ne(SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        return ne(true, column1, column2);
    }

    /**
     * 小于等于 column1 <> column2
     *
     * @param condition
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain ne(boolean condition, SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        if (condition) {
            getFilter().ne(column1.getTable(), column1.getValue(), column2.getTable(), column2.getValue());
        }
        return castChain();
    }

    /**
     * 小于等于 column1 <= column2
     *
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain le(SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        return le(true, column1, column2);
    }

    /**
     * 小于等于 column1 <= column2
     *
     * @param condition
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain le(boolean condition, SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        if (condition) {
            getFilter().le(column1.getTable(), column1.getValue(), column2.getTable(), column2.getValue());
        }
        return castChain();
    }

    /**
     * 小于 column < val
     *
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain lt(SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        return lt(true, column1, column2);
    }

    /**
     * 小于 column < val
     *
     * @param condition
     * @param column1
     * @param column2
     * @param <TProperty>
     * @return
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2, TProperty> TChain lt(boolean condition, SQLColumn<T1Proxy, TProperty> column1, SQLColumn<T2Proxy, TProperty> column2) {
        if (condition) {
            getFilter().lt(column1.getTable(), column1.getValue(), column2.getTable(), column2.getValue());
        }
        return castChain();
    }
}
