package com.easy.query.core.expression.parser.core.base.core.filter;

import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;

/**
 * create time 2023/9/25 17:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AssertPredicate<T1, TChain> extends EntitySQLTableOwner<T1>, FilterAvailable, ChainCast<TChain> {
    /**
     * column is null
     */
    default TChain isNull(String property) {
        return isNull(true, property);
    }

    /**
     * column is null
     *
     * @param condition 执行条件
     * @param property  字段
     * @return children
     */
   default TChain isNull(boolean condition, String property){
       if (condition) {
           getFilter().isNull(getTable(), property);
       }
       return castChain();
   }

    /**
     * column is not null
     */
    default TChain isNotNull(String property) {
        return isNotNull(true, property);
    }

    /**
     * column is not null
     *
     * @param condition 执行条件
     * @param property  字段
     * @return children
     */
   default TChain isNotNull(boolean condition, String property){
       if (condition) {
           getFilter().isNotNull(getTable(), property);
       }
       return castChain();
   }
    /**
     * column is null or empty
     */
    default TChain isEmpty(String property) {
        return isEmpty(true, property);
    }

    /**
     * column is null or empty
     *
     * @param condition 执行条件
     * @param property  字段
     * @return children
     */
    TChain isEmpty(boolean condition, String property);

    /**
     * column is not null and not empty
     */
    default TChain isNotEmpty(String property) {
        return isNotEmpty(true, property);
    }

    /**
     * column is not null and not empty
     *
     * @param condition 执行条件
     * @param property  字段
     * @return children
     */
    TChain isNotEmpty(boolean condition, String property);
    /**
     * column is null or empty
     */
    default TChain isBlank(String property) {
        return isBlank(true, property);
    }

    /**
     * column is null or empty
     *
     * @param condition 执行条件
     * @param property  字段
     * @return children
     */
    TChain isBlank(boolean condition, String property);

    /**
     * column is not null and not empty
     */
    default TChain isNotBlank(String property) {
        return isNotBlank(true, property);
    }

    /**
     * column is not null and not empty
     *
     * @param condition 执行条件
     * @param property  字段
     * @return children
     */
    TChain isNotBlank(boolean condition, String property);
}
