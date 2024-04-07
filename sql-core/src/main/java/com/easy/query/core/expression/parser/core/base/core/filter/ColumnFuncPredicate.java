package com.easy.query.core.expression.parser.core.base.core.filter;

import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/10/12 22:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFuncPredicate<T1, TChain> extends EntitySQLTableOwner<T1>, FilterAvailable, ChainCast<TChain> {

    /**
     * 大于 func(column) > val
     */
    default TChain gt(String property, SQLFunction sqlFunction) {
        return gt(true, property,sqlFunction);
    }

    /**
     * 大于 func(column) > val
     *
     * @param condition 执行条件
     * @param property  属性列
     * @param sqlFunction 函数
     * @return children
     */
    default TChain gt(boolean condition, String property, SQLFunction sqlFunction) {

        if (condition) {
            getFilter().gt(getTable(), property,getTable(), sqlFunction);
        }
        return castChain();
    }

    /**
     * 等于 func(column) >= val
     */
    default TChain ge(String property,SQLFunction sqlFunction) {
        return ge(true, property,sqlFunction);
    }

    /**
     * 等于 func(column) >= val
     *
     * @param condition 执行条件
     * @param sqlFunction 函数
     * @return children
     */
    default TChain ge(boolean condition,String property, SQLFunction sqlFunction){

        if (condition) {
            getFilter().ge(getTable(),property,getTable(), sqlFunction);
        }
        return castChain();
    }

    /**
     * 等于 func(column) = val
     */
    default TChain eq(String property,SQLFunction sqlFunction) {
        return eq(true,property, sqlFunction);
    }

    /**
     * 等于 func(column) = val
     *
     * @param condition 执行条件
     * @param sqlFunction 函数
     * @return children
     */
    default TChain eq(boolean condition,String property, SQLFunction sqlFunction) {

        if (condition) {
            getFilter().eq(getTable(),property,getTable(), sqlFunction);
        }
        return castChain();
    }


    /**
     * 不等于 func(column) <> val
     */
    default TChain ne(String property,SQLFunction sqlFunction) {
        return ne(true,property, sqlFunction);
    }

    /**
     * 不等于 func(column) <> val
     *
     * @param condition 执行条件
     * @param sqlFunction 函数
     * @return children
     */
    default TChain ne(boolean condition,String property, SQLFunction sqlFunction){
        if (condition) {
            getFilter().ne(getTable(),property,getTable(), sqlFunction);
        }
        return castChain();
    }

    /**
     * 小于等于 func(column) <= val
     */
    default TChain le(String property,SQLFunction sqlFunction) {
        return le(true,property, sqlFunction);
    }

    /**
     * 小于等于 func(column) <= val
     *
     * @param condition 执行条件
     * @param sqlFunction 函数
     * @return children
     */
    default TChain le(boolean condition,String property, SQLFunction sqlFunction){
        if (condition) {
            getFilter().le(getTable(),property,getTable(), sqlFunction);
        }
        return castChain();
    }

    /**
     * 小于 func(column) < val
     */
    default TChain lt(String property,SQLFunction sqlFunction) {
        return lt(true,property, sqlFunction);
    }

    /**
     * 小于 func(column) < val
     *
     * @param condition 执行条件
     * @param sqlFunction 字段
     * @return children
     */
    default TChain lt(boolean condition,String property, SQLFunction sqlFunction){
        if (condition) {
            getFilter().lt(getTable(),property,getTable(), sqlFunction);
        }
        return castChain();
    }
}
