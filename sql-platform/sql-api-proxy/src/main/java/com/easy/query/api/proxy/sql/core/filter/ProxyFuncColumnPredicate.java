package com.easy.query.api.proxy.sql.core.filter;

import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.filter.FilterAvailable;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyFuncColumnPredicate<TChain> extends FilterAvailable, ChainCast<TChain> {

    /**
     * 大于 func(column) > func(column|val)
     *
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain gt(SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {
        return gt(true, sqlFunctionLeft, sqlFunctionRight);
    }

    /**
     * 大于 func(column) > func(column|val)
     *
     * @param condition
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain gt(boolean condition, SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {

        if (condition) {
            //因为sqlColumn使用的都是自己的表所以这边传递什么表都是没有关系的
            getFilter().gt(null, sqlFunctionLeft, null, sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 等于 func(column) >= func(column|val)
     *
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain ge(SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {
        return ge(true, sqlFunctionLeft, sqlFunctionRight);
    }

    /**
     * 等于 func(column) >= func(column|val)
     *
     * @param condition
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain ge(boolean condition, SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {

        if (condition) {
            getFilter().ge(null, sqlFunctionLeft, null, sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 等于 func(column) = func(column|val)
     */
    default TChain eq(SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {
        return eq(true, sqlFunctionLeft, sqlFunctionRight);
    }

    /**
     * 等于 func(column) = func(column|val)
     *
     * @param condition
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain eq(boolean condition, SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {

        if (condition) {
            getFilter().eq(null, sqlFunctionLeft, null, sqlFunctionRight);
        }
        return castChain();
    }


    /**
     * 不等于 func(column) <> func(column|val)
     *
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain ne(SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {
        return ne(true, sqlFunctionLeft, sqlFunctionRight);
    }

    /**
     * 不等于 func(column) <> func(column|val)
     *
     * @param condition
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain ne(boolean condition, SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {
        if (condition) {
            getFilter().ne(null, sqlFunctionLeft, null, sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 小于等于 func(column) <= func(column|val)
     *
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain le(SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {
        return le(true, sqlFunctionLeft, sqlFunctionRight);
    }

    /**
     * 小于等于 func(column) <= func(column|val)
     *
     * @param condition
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain le(boolean condition, SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {
        if (condition) {
            getFilter().le(null, sqlFunctionLeft, null, sqlFunctionRight);
        }
        return castChain();
    }

    /**
     * 小于 func(column) < func(column|val)
     *
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain lt(SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {
        return lt(true, sqlFunctionLeft, sqlFunctionRight);
    }

    /**
     * 小于 func(column) < func(column|val)
     *
     * @param condition
     * @param sqlFunctionLeft
     * @param sqlFunctionRight
     * @return
     */
    default TChain lt(boolean condition, SQLFunction sqlFunctionLeft, SQLFunction sqlFunctionRight) {
        if (condition) {
            getFilter().lt(null, sqlFunctionLeft, null, sqlFunctionRight);
        }
        return castChain();
    }
}
