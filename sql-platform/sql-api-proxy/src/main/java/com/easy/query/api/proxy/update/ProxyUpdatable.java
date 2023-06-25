package com.easy.query.api.proxy.update;

import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/25 19:05
 * 文件说明
 *
 * @author xuejiaming
 */

public interface ProxyUpdatable<TProxy extends ProxyEntity<TProxy,T>,T,TChain> extends Updatable<T,TChain> {
    TProxy getProxy();
}
