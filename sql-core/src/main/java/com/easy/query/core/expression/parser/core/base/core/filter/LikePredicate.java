package com.easy.query.core.expression.parser.core.base.core.filter;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;

/**
 * create time 2023/9/25 17:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface LikePredicate<T1, TChain> extends EntitySQLTableOwner<T1>, FilterAvailable, ChainCast<TChain> {

    /**
     * column like val%
     * 列匹配前半部分
     *
     * @param property
     * @param val
     * @return
     */
    default TChain likeMatchLeft(String property, Object val) {
        return likeMatchLeft(true, property, val);
    }

    /**
     * column like val%
     * 列匹配前半部分
     *
     * @param condition
     * @param property
     * @param val
     * @return
     */
    default TChain likeMatchLeft(boolean condition, String property, Object val) {
        return like(condition, property, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column like %val
     * 列匹配后半部分
     *
     * @param property
     * @param val
     * @return
     */
    default TChain likeMatchRight(String property, Object val) {
        return likeMatchRight(true, property, val);
    }

    /**
     * column like %val
     * 列匹配后半部分
     *
     * @param condition
     * @param property
     * @param val
     * @return
     */
    default TChain likeMatchRight(boolean condition, String property, Object val) {
        return like(condition, property, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column like %val%
     * 列全匹配
     */
    default TChain like(String property, Object val) {
        return like(true, property, val);
    }

    /**
     * column like %val%
     * 列全匹配
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
    default TChain like(boolean condition, String property, Object val) {
        return like(condition, property, val, SQLLikeEnum.LIKE_PERCENT_ALL);
    }

    /**
     * column like ?val?
     * 列自定义匹配
     *
     * @param condition
     * @param property
     * @param val
     * @param sqlLike
     * @return
     */
   default TChain like(boolean condition, String property, Object val, SQLLikeEnum sqlLike){
       if (condition) {
           getFilter().like(getTable(), property, val, sqlLike);
       }
       return castChain();
   }

    /**
     * column not like val%
     *
     * @param property
     * @param val
     * @return
     */
    default TChain notLikeMatchLeft(String property, Object val) {
        return notLikeMatchLeft(true, property, val);
    }

    /**
     * column not like val%
     *
     * @param condition
     * @param property
     * @param val
     * @return
     */
    default TChain notLikeMatchLeft(boolean condition, String property, Object val) {
        return notLike(condition, property, val, SQLLikeEnum.LIKE_PERCENT_RIGHT);
    }

    /**
     * column not like %val
     *
     * @param property
     * @param val
     * @return
     */
    default TChain notLikeMatchRight(String property, Object val) {
        return notLikeMatchRight(true, property, val);
    }

    /**
     * column not like %val
     *
     * @param condition
     * @param property
     * @param val
     * @return
     */
    default TChain notLikeMatchRight(boolean condition, String property, Object val) {
        return notLike(condition, property, val, SQLLikeEnum.LIKE_PERCENT_LEFT);
    }

    /**
     * column not like %val%
     */
    default TChain notLike(String property, Object val) {
        return notLike(true, property, val);
    }

    /**
     * column not like %val%
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
    default TChain notLike(boolean condition, String property, Object val) {
        return notLike(condition, property, val, SQLLikeEnum.LIKE_PERCENT_ALL);
    }

   default TChain notLike(boolean condition, String property, Object val, SQLLikeEnum sqlLike){
       if (condition) {
           getFilter().notLike(getTable(), property, val, sqlLike);
       }
       return castChain();
   }
}
