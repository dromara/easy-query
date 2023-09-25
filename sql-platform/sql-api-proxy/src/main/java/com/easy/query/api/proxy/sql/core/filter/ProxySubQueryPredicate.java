package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/9/25 16:48
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySubQueryPredicate<TChain> extends FilterAvailable, ChainCast<TChain> {
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain gt(SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        return gt(true, column, subQuery);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain gt(boolean condition, SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        if (condition) {
            getFilter().gt(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain ge(SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        return ge(true, column, subQuery);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain ge(boolean condition, SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        if (condition) {
            getFilter().ge(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain eq(SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        return eq(true, column, subQuery);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain eq(boolean condition, SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        if (condition) {
            getFilter().eq(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain ne(SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        return ne(true, column, subQuery);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain ne(boolean condition, SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        if (condition) {
            getFilter().ne(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain le(SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        return le(true, column, subQuery);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain le(boolean condition, SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        if (condition) {
            getFilter().le(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain lt(SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        return lt(true, column, subQuery);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain lt(boolean condition, SQLColumn<TPropertyProxy,TProperty> column, ProxyQueryable<TPropertyProxy,TProperty> subQuery) {
        if (condition) {
            getFilter().lt(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }

    /**
     * column in (select column from table)
     *
     * @param column
     * @param subQueryable
     * @param <TProxy>
     * @param <TProperty>
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, TProperty>, TProperty> TChain in(SQLColumn<TProxy, TProperty> column, ProxyQueryable<TProxy, TProperty> subQueryable) {
        return in(true, column, subQueryable);
    }

    /**
     * column in (select column from table)
     *
     * @param condition
     * @param column
     * @param subQueryable
     * @param <TProxy>
     * @param <TProperty>
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, TProperty>, TProperty> TChain in(boolean condition, SQLColumn<TProxy, TProperty> column, ProxyQueryable<TProxy, TProperty> subQueryable) {
        if (condition) {
            getFilter().in(column.getTable(), column.value(), subQueryable);
        }
        return castChain();
    }
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain notIn(SQLColumn<TPropertyProxy, TProperty> column, ProxyQueryable<TPropertyProxy, TProperty> subQueryable) {
        return notIn(true, column, subQueryable);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TChain notIn(boolean condition, SQLColumn<TPropertyProxy, TProperty> column, ProxyQueryable<TPropertyProxy, TProperty> subQueryable) {
        if (condition) {
            getFilter().notIn(column.getTable(), column.value(), subQueryable);
        }
        return castChain();
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> TChain exists(T1Proxy tableProxy, ProxyQueryable<T2Proxy, T2> subQueryable) {
        return exists(true, tableProxy, subQueryable);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> TChain exists(boolean condition, T1Proxy tableProxy, ProxyQueryable<T2Proxy, T2> subQueryable) {
        if (condition) {
            getFilter().exists(tableProxy.getTable(), subQueryable);
        }
        return castChain();
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> TChain notExists(T1Proxy tableProxy, ProxyQueryable<T2Proxy, T2> subQueryable) {
        return notExists(true, tableProxy, subQueryable);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> TChain notExists(boolean condition, T1Proxy tableProxy, ProxyQueryable<T2Proxy, T2> subQueryable) {
        if (condition) {
            getFilter().notExists(tableProxy.getTable(), subQueryable);
        }
        return castChain();
    }
}
