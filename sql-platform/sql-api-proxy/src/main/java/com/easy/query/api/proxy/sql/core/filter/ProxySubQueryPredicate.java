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
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain gt(SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        return gt(true, column, subQuery);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain gt(boolean condition, SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        if (condition) {
            getFilter().gt(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain ge(SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        return ge(true, column, subQuery);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain ge(boolean condition, SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        if (condition) {
            getFilter().ge(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain eq(SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        return eq(true, column, subQuery);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain eq(boolean condition, SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        if (condition) {
            getFilter().eq(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain ne(SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        return ne(true, column, subQuery);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain ne(boolean condition, SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        if (condition) {
            getFilter().ne(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain le(SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        return le(true, column, subQuery);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain le(boolean condition, SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        if (condition) {
            getFilter().le(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain lt(SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        return lt(true, column, subQuery);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain lt(boolean condition, SQLColumn<T1Proxy,TProperty> column, ProxyQueryable<T2Proxy,TProperty> subQuery) {
        if (condition) {
            getFilter().lt(column.getTable(),column.value(), subQuery);
        }
        return castChain();
    }

    /**
     * column in (select column from table)
     * @param column
     * @param subQueryable
     * @return
     * @param <T1Proxy>
     * @param <T1>
     * @param <T2Proxy>
     * @param <TProperty>
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain in(SQLColumn<T1Proxy, TProperty> column, ProxyQueryable<T2Proxy, TProperty> subQueryable) {
        return in(true, column, subQueryable);
    }

    /**
     * column in (select column from table)
     * @param condition
     * @param column
     * @param subQueryable
     * @return
     * @param <T1Proxy>
     * @param <T1>
     * @param <T2Proxy>
     * @param <TProperty>
     */
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain in(boolean condition, SQLColumn<T1Proxy, TProperty> column, ProxyQueryable<T2Proxy, TProperty> subQueryable) {
        if (condition) {
            getFilter().in(column.getTable(), column.value(), subQueryable);
        }
        return castChain();
    }
    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain notIn(SQLColumn<T1Proxy, TProperty> column, ProxyQueryable<T2Proxy, TProperty> subQueryable) {
        return notIn(true, column, subQueryable);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, TProperty>, TProperty> TChain notIn(boolean condition, SQLColumn<T1Proxy, TProperty> column, ProxyQueryable<T2Proxy, TProperty> subQueryable) {
        if (condition) {
            getFilter().notIn(column.getTable(), column.value(), subQueryable);
        }
        return castChain();
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> TChain exists(ProxyQueryable<T2Proxy, T2> subQueryable) {
        return exists(true, subQueryable);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> TChain exists(boolean condition, ProxyQueryable<T2Proxy, T2> subQueryable) {
        if (condition) {
            getFilter().exists(subQueryable);
        }
        return castChain();
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> TChain notExists(ProxyQueryable<T2Proxy, T2> subQueryable) {
        return notExists(true, subQueryable);
    }

    default <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> TChain notExists(boolean condition, ProxyQueryable<T2Proxy, T2> subQueryable) {
        if (condition) {
            getFilter().notExists(subQueryable);
        }
        return castChain();
    }
}
