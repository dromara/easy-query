package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * create time 2023/8/17 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientKtQueryableAvailable<T1> {
    ClientQueryable<T1> getClientQueryable();
}
