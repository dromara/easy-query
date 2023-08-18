package com.easy.query.api4kt.select.impl;

import com.easy.query.api4kt.select.KtQueryable8;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable8;

/**
 * create time 2023/8/18 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyKtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> extends AbstractKtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> {
    public EasyKtQueryable8(ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8) {
        super(entityQueryable8);
    }

    @Override
    public KtQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> cloneQueryable() {
        return new EasyKtQueryable8<>(entityQueryable8.cloneQueryable());
    }
}