package com.easy.query.core.basic.api.select;

/**
 * create time 2024/7/28 10:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientQueryableAvailable<T> extends Query<T> {
    ClientQueryable<T> getClientQueryable();
}
