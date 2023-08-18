package com.easy.query.api4kt.select.impl;

import com.easy.query.api4kt.select.KtQueryable9;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable9;
import com.easy.query.core.basic.api.select.ClientQueryable9;

/**
 * create time 2023/8/18 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyKtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends AbstractKtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
    public EasyKtQueryable9(ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9) {
        super(entityQueryable9);
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> cloneQueryable() {
        return new EasyKtQueryable9<>(entityQueryable9.cloneQueryable());
    }
}
