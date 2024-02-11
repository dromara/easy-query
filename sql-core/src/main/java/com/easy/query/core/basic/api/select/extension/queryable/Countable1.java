package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;

import java.math.BigDecimal;

/**
 * create time 2023/9/14 22:28
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Countable1<T1> {


    /**
     * SELECT COUNT(*) FROM TABLE
     */
    ClientQueryable<Long> selectCount();


    /**
     * SELECT COUNT(*) FROM TABLE
     *
     * @param numberClass 自定义返回结果字节
     * @param <TNumber>   返回结果类型
     * @return 返回当前queryable链式
     */
    <TNumber extends Number> ClientQueryable<TNumber> selectCount(Class<TNumber> numberClass);

    <TMember extends Number> ClientQueryable<TMember> selectSum(Class<TMember> numberClass, String property);

    default ClientQueryable<BigDecimal> selectSumBigDecimal(String property) {
        return selectSum(BigDecimal.class, property);
    }

    ClientQueryable<BigDecimal> selectAvg(String property);
   <TMember> ClientQueryable<TMember> selectMax(Class<TMember> memberClass,String property);
   <TMember> ClientQueryable<TMember> selectMin(Class<TMember> memberClass,String property);

}
