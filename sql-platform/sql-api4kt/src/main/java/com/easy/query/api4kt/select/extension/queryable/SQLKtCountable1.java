package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.api4kt.select.KtQueryable;

/**
 * create time 2023/9/23 22:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtCountable1<T1> {
    /**
     * SELECT COUNT(*) FROM TABLE
     * @return
     */
    KtQueryable<Long> selectCount();

    /**
     * SELECT COUNT(*) FROM TABLE
     * @param numberClass 自定义返回结果字节
     * @return 返回当前queryable链式
     * @param <TNumber> 返回结果类型
     */
    <TNumber extends Number> KtQueryable<TNumber> selectCount(Class<TNumber> numberClass);
}
