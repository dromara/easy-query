package com.easy.query.api.proxy.delete;

import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/25 21:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyDeletable<TProxy extends ProxyEntity<TProxy,T>,T,TChain> extends Deletable<T,TChain> {
    TProxy getProxy();
}
