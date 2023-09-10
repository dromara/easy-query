package com.easy.query.api.proxy.select;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.List;

/**
 * create time 2023/9/10 09:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyBaseQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {

    ClientQueryable<T1> getClientQueryable();

    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(TRProxy trProxy);
}
