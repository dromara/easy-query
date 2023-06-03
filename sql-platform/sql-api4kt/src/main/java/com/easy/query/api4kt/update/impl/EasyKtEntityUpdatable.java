package com.easy.query.api4kt.update.impl;

import com.easy.query.api4kt.update.abstraction.AbstractKtEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;

/**
 * @author xuejiaming
 * @FileName: EasyEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:20
 */
public class EasyKtEntityUpdatable<T> extends AbstractKtEntityUpdatable<T> {

    public EasyKtEntityUpdatable(ClientEntityUpdatable<T> entityObjectUpdatable) {
        super(entityObjectUpdatable);
    }
}
