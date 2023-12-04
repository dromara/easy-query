package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * create time 2023/8/17 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientEntityQueryableAvailable<T1> {
    ClientQueryable<T1> getClientQueryable();
}
