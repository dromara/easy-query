package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;

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
     * @param numberClass 自定义返回结果字节
     * @return 返回当前queryable链式
     * @param <TNumber> 返回结果类型
     */
    <TNumber extends Number> ClientQueryable<TNumber> selectCount(Class<TNumber> numberClass);

}
