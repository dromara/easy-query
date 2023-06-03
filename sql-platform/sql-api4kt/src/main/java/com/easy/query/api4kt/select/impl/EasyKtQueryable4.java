package com.easy.query.api4kt.select.impl;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable4;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 */
public class EasyKtQueryable4<T1, T2, T3, T4> extends AbstractKtQueryable4<T1, T2, T3, T4> {
    public EasyKtQueryable4(ClientQueryable4<T1, T2, T3, T4> entityQueryable4) {
        super(entityQueryable4);
    }

    @Override
    public KtQueryable<T1> cloneQueryable() {
        return new EasyKtQueryable<>(entityQueryable4);
    }
}
