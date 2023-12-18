package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.core.basic.api.select.Query;

/**
 * create time 2023/12/18 09:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityCountable1<T1> {


    /**
     * SELECT COUNT(*) FROM TABLE
     */
    default Query<Long> selectCount(){
        return selectCount(Long.class);
    }


    /**
     * SELECT COUNT(*) FROM TABLE
     * @param numberClass 自定义返回结果字节
     * @return 返回当前queryable链式
     * @param <TNumber> 返回结果类型
     */
    <TNumber extends Number> Query<TNumber> selectCount(Class<TNumber> numberClass);
}
