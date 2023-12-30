package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/10/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyColumnFunc2FuncAvailablePredicate<TChain> extends FilterAvailable, ChainCast<TChain> {

    /**
     * 大于 column > func(column|val)
     *
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain gt(DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {
        return gt(true, dslsqlFunctionAvailableLeft, dslsqlFunctionAvailableRight);
    }

    /**
     * 大于 column > func(column|val)
     *
     * @param condition
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain gt(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {

        if (condition) {
            //因为sqlColumn使用的都是自己的表所以这边传递什么表都是没有关系的

            SQLFunction sqlFunctionLeft = dslsqlFunctionAvailableLeft.func().apply(getFilter().getRuntimeContext().fx());
            SQLFunction sqlFunctionRight = dslsqlFunctionAvailableRight.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().gt(dslsqlFunctionAvailableLeft.getTable(), sqlFunctionLeft, dslsqlFunctionAvailableRight.getTable(), sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 等于 column >= func(column|val)
     *
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain ge(DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {
        return ge(true, dslsqlFunctionAvailableLeft, dslsqlFunctionAvailableRight);
    }

    /**
     * 等于 column >= func(column|val)
     *
     * @param condition
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain ge(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {

        if (condition) {
            SQLFunction sqlFunctionLeft = dslsqlFunctionAvailableLeft.func().apply(getFilter().getRuntimeContext().fx());
            SQLFunction sqlFunctionRight = dslsqlFunctionAvailableRight.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().ge(dslsqlFunctionAvailableLeft.getTable(), sqlFunctionLeft, dslsqlFunctionAvailableRight.getTable(), sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 等于 column = func(column|val)
     */
    default TChain eq(DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {
        return eq(true, dslsqlFunctionAvailableLeft, dslsqlFunctionAvailableRight);
    }

    /**
     * 等于 column = func(column|val)
     *
     * @param condition
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain eq(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {

        if (condition) {
            SQLFunction sqlFunctionLeft = dslsqlFunctionAvailableLeft.func().apply(getFilter().getRuntimeContext().fx());
            SQLFunction sqlFunctionRight = dslsqlFunctionAvailableRight.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().eq(dslsqlFunctionAvailableLeft.getTable(), sqlFunctionLeft, dslsqlFunctionAvailableRight.getTable(), sqlFunctionRight);
        }
        return castChain();
    }


    /**
     * 不等于 column <> func(column|val)
     *
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain ne(DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {
        return ne(true, dslsqlFunctionAvailableLeft, dslsqlFunctionAvailableRight);
    }

    /**
     * 不等于 column <> func(column|val)
     *
     * @param condition
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain ne(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {
        if (condition) {
            SQLFunction sqlFunctionLeft = dslsqlFunctionAvailableLeft.func().apply(getFilter().getRuntimeContext().fx());
            SQLFunction sqlFunctionRight = dslsqlFunctionAvailableRight.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().ne(dslsqlFunctionAvailableLeft.getTable(), sqlFunctionLeft, dslsqlFunctionAvailableRight.getTable(), sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 小于等于 column <= func(column|val)
     *
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain le(DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {
        return le(true, dslsqlFunctionAvailableLeft, dslsqlFunctionAvailableRight);
    }

    /**
     * 小于等于 column <= func(column|val)
     *
     * @param condition
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain le(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {
        if (condition) {
            SQLFunction sqlFunctionLeft = dslsqlFunctionAvailableLeft.func().apply(getFilter().getRuntimeContext().fx());
            SQLFunction sqlFunctionRight = dslsqlFunctionAvailableRight.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().le(dslsqlFunctionAvailableLeft.getTable(), sqlFunctionLeft, dslsqlFunctionAvailableRight.getTable(), sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 小于 column < func(column|val)
     *
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain lt(DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {
        return lt(true, dslsqlFunctionAvailableLeft, dslsqlFunctionAvailableRight);
    }

    /**
     * 小于 column < func(column|val)
     *
     * @param condition
     * @param dslsqlFunctionAvailableLeft
     * @param dslsqlFunctionAvailableRight
     * @return
     */
    default TChain lt(boolean condition, DSLSQLFunctionAvailable dslsqlFunctionAvailableLeft, DSLSQLFunctionAvailable dslsqlFunctionAvailableRight) {
        if (condition) {
            SQLFunction sqlFunctionLeft = dslsqlFunctionAvailableLeft.func().apply(getFilter().getRuntimeContext().fx());
            SQLFunction sqlFunctionRight = dslsqlFunctionAvailableRight.func().apply(getFilter().getRuntimeContext().fx());
            getFilter().lt(dslsqlFunctionAvailableLeft.getTable(), sqlFunctionLeft, dslsqlFunctionAvailableRight.getTable(), sqlFunctionRight);
        }
        return castChain();
    }
}
