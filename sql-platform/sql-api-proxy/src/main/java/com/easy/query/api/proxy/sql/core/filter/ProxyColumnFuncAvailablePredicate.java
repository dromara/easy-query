package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/10/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyColumnFuncAvailablePredicate<TChain> extends FilterAvailable, ChainCast<TChain> {

    /**
     * 大于 column > func(column|val)
     *
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain gt(SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {
        return gt(true, column, dslsqlFunctionAvailable);
    }

    /**
     * 大于 column > func(column|val)
     *
     * @param condition
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain gt(boolean condition, SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {

        if (condition) {
            //因为sqlColumn使用的都是自己的表所以这边传递什么表都是没有关系的

            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().gt(column.getTable(), column.getValue(), null, sqlFunction);
        }
        return castChain();
    }

    /**
     * 等于 column >= func(column|val)
     *
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ge(SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {
        return ge(true, column, dslsqlFunctionAvailable);
    }

    /**
     * 等于 column >= func(column|val)
     *
     * @param condition
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ge(boolean condition, SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {

        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().ge(column.getTable(), column.getValue(), null, sqlFunction);
        }
        return castChain();
    }

    /**
     * 等于 column = func(column|val)
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain eq(SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {
        return eq(true, column, dslsqlFunctionAvailable);
    }

    /**
     * 等于 column = func(column|val)
     *
     * @param condition
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain eq(boolean condition, SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {

        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().eq(column.getTable(), column.getValue(), null, sqlFunction);
        }
        return castChain();
    }


    /**
     * 不等于 column <> func(column|val)
     *
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ne(SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {
        return ne(true, column, dslsqlFunctionAvailable);
    }

    /**
     * 不等于 column <> func(column|val)
     *
     * @param condition
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain ne(boolean condition, SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {
        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().ne(column.getTable(), column.getValue(), null, sqlFunction);
        }
        return castChain();
    }

    /**
     * 小于等于 column <= func(column|val)
     *
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain le(SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {
        return le(true, column, dslsqlFunctionAvailable);
    }

    /**
     * 小于等于 column <= func(column|val)
     *
     * @param condition
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain le(boolean condition, SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {
        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().le(column.getTable(), column.getValue(), null, sqlFunction);
        }
        return castChain();
    }

    /**
     * 小于 column < func(column|val)
     *
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain lt(SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {
        return lt(true, column, dslsqlFunctionAvailable);
    }

    /**
     * 小于 column < func(column|val)
     *
     * @param condition
     * @param column
     * @param dslsqlFunctionAvailable
     * @return
     */
    default <TProxy extends ProxyEntity<TProxy, T>, T, TProperty> TChain lt(boolean condition, SQLColumn<TProxy, TProperty> column, DSLSQLFunctionAvailable dslsqlFunctionAvailable) {
        if (condition) {
            SQLFunction sqlFunction = dslsqlFunctionAvailable.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().lt(column.getTable(), column.getValue(), null, sqlFunction);
        }
        return castChain();
    }
}
