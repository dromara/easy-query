package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.select.Queryable;

/**
 * create time 2023/9/23 22:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLCountable1<T1> {


    /**
     * SELECT COUNT(*) FROM TABLE
     */
    Queryable<Long> selectCount();


    /**
     * SELECT COUNT(*) FROM TABLE
     * @param numberClass 自定义返回结果字节
     * @return 返回当前queryable链式
     * @param <TNumber> 返回结果类型
     */
    <TNumber extends Number> Queryable<TNumber> selectCount(Class<TNumber> numberClass);
}
