package com.easy.query.api.proxy.entity.insert;

import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/12/7 13:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEntityInsertable<TProxy extends ProxyEntity<TProxy, T>, T> extends AbstractEntityInsertable<TProxy, T> {
    public EasyEntityInsertable(TProxy tProxy, ClientInsertable<T> clientInsertable) {
        super(tProxy, clientInsertable);
    }
}
