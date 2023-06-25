package com.easy.query.api.proxy.insert;

import com.easy.query.core.basic.api.insert.ClientInsertable;

/**
 * create time 2023/6/2 16:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyEntityInsertable<T> extends AbstractProxyEntityInsertable<T> {
    public EasyProxyEntityInsertable(ClientInsertable<T> clientInsertable) {
        super(clientInsertable);
    }
}
