package com.easy.query.api4kt.insert;

import com.easy.query.core.basic.api.insert.ClientInsertable;

/**
 * create time 2023/6/2 16:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyKtEntityInsertable<T> extends AbstractKtEntityInsertable<T> {
    public EasyKtEntityInsertable(ClientInsertable<T> clientInsertable) {
        super(clientInsertable);
    }
}
