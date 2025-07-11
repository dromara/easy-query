package com.easy.query.core.expression.parser.core.base.core.filter;

import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/9/25 16:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ValuePredicate<T1, TChain> extends EntitySQLTableOwner<T1>, FilterAvailable, ChainCast<TChain> {

    TChain multiEq(boolean condition, String[] properties, List<Object> vals);

    /**
     * 大于 column > val
     */
    default TChain gt(String property, Object val) {
        return gt(true, property, val);
    }

    /**
     * 大于 column > val
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
    default TChain gt(boolean condition, String property, Object val) {

        if (condition) {
            getFilter().gt(getTable(), property, val);
        }
        return castChain();
    }

    /**
     * 等于 column >= val
     */
    default TChain ge(String property, Object val) {
        return ge(true, property, val);
    }

    /**
     * 等于 column >= val
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
   default TChain ge(boolean condition, String property, Object val){

       if (condition) {
           getFilter().ge(getTable(), property, val);
       }
       return castChain();
   }

    /**
     * 等于 column = val
     */
    default TChain eq(String property, Object val) {
        return eq(true, property, val);
    }

    /**
     * 等于 column = val
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
    default TChain eq(boolean condition, String property, Object val) {

        if (condition) {
            getFilter().eq(getTable(), property, val);
        }
        return castChain();
    }
    default TChain eqColumn(boolean condition, String columnName, Object val) {

        if (condition) {
            getFilter().eqColumn(getTable(), columnName, val);
        }
        return castChain();
    }


    /**
     * 不等于 column <> val
     */
    default TChain ne(String property, Object val) {
        return ne(true, property, val);
    }

    /**
     * 不等于 column <> val
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
   default TChain ne(boolean condition, String property, Object val){
       if (condition) {
           getFilter().ne(getTable(), property, val);
       }
       return castChain();
   }

    /**
     * 小于等于 column <= val
     */
    default TChain le(String property, Object val) {
        return le(true, property, val);
    }

    /**
     * 小于等于 column <= val
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
    default TChain le(boolean condition, String property, Object val){
        if (condition) {
            getFilter().le(getTable(), property, val);
        }
        return castChain();
    }

    /**
     * 小于 column < val
     */
    default TChain lt(String property, Object val) {
        return lt(true, property, val);
    }

    /**
     * 小于 column < val
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
   default TChain lt(boolean condition, String property, Object val){
       if (condition) {
           getFilter().lt(getTable(), property, val);
       }
       return castChain();
   }

}
