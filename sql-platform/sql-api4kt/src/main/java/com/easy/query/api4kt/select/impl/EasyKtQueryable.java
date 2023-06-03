package com.easy.query.api4kt.select.impl;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable1;
import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:28
 */
public class EasyKtQueryable<T> extends AbstractKtQueryable1<T> {
    public EasyKtQueryable(ClientQueryable<T> entityQueryable) {
        super(entityQueryable);
    }

    @Override
    public KtQueryable<T> cloneQueryable() {
        return new EasyKtQueryable<>(entityQueryable.cloneQueryable());
    }
}
