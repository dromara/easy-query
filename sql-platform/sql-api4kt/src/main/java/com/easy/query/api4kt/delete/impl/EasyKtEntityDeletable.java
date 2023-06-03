package com.easy.query.api4kt.delete.impl;

import com.easy.query.api4kt.delete.abstraction.AbstractKtEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:20
 */
public class EasyKtEntityDeletable<T> extends AbstractKtEntityDeletable<T> {
    public EasyKtEntityDeletable(ClientEntityDeletable<T> entityObjectDeletable) {
        super(entityObjectDeletable);
    }
}
