package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/10/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
@Deprecated
public interface ProxyColumnFuncPredicate<TChain> extends FilterAvailable, ChainCast<TChain> {

    /**
     * 大于 column > func(column|val)
     *
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain gt(SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {
        return gt(true, column, sqlFunctionRight);
    }

    /**
     * 大于 func(column) > func(column|val)
     *
     * @param condition
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain gt(boolean condition, SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {

        if (condition) {
            //因为sqlColumn使用的都是自己的表所以这边传递什么表都是没有关系的
            getFilter().gt(column.getTable(), column.getValue(), null, sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 等于 func(column) >= func(column|val)
     *
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ge(SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {
        return ge(true, column, sqlFunctionRight);
    }

    /**
     * 等于 func(column) >= func(column|val)
     *
     * @param condition
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ge(boolean condition, SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {

        if (condition) {
            getFilter().ge(column.getTable(), column.getValue(), null, sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 等于 func(column) = func(column|val)
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain eq(SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {
        return eq(true, column, sqlFunctionRight);
    }

    /**
     * 等于 func(column) = func(column|val)
     *
     * @param condition
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain eq(boolean condition, SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {

        if (condition) {
            getFilter().eq(column.getTable(), column.getValue(), null, sqlFunctionRight);
        }
        return castChain();
    }


    /**
     * 不等于 func(column) <> func(column|val)
     *
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ne(SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {
        return ne(true, column, sqlFunctionRight);
    }

    /**
     * 不等于 func(column) <> func(column|val)
     *
     * @param condition
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ne(boolean condition, SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {
        if (condition) {
            getFilter().ne(column.getTable(), column.getValue(), null, sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 小于等于 func(column) <= func(column|val)
     *
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain le(SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {
        return le(true, column, sqlFunctionRight);
    }

    /**
     * 小于等于 func(column) <= func(column|val)
     *
     * @param condition
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain le(boolean condition, SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {
        if (condition) {
            getFilter().le(column.getTable(), column.getValue(), null, sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 小于 func(column) < func(column|val)
     *
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain lt(SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {
        return lt(true, column, sqlFunctionRight);
    }

    /**
     * 小于 func(column) < func(column|val)
     *
     * @param condition
     * @param column
     * @param sqlFunctionRight
     * @return
     */
    @Deprecated
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain lt(boolean condition, SQLColumn<TProxy, TProperty> column, SQLFunction sqlFunctionRight) {
        if (condition) {
            getFilter().lt(column.getTable(), column.getValue(), null, sqlFunctionRight);
        }
        return castChain();
    }
}
