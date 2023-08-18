package com.easy.query.api4kt.select.impl;

import com.easy.query.api4kt.select.KtQueryable7;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable7;
import com.easy.query.core.basic.api.select.ClientQueryable7;

/**
 * create time 2023/8/18 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyKtQueryable7<T1, T2, T3, T4, T5, T6, T7> extends AbstractKtQueryable7<T1, T2, T3, T4, T5, T6, T7> {
    public EasyKtQueryable7(ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7) {
        super(entityQueryable7);
    }

    @Override
    public KtQueryable7<T1, T2, T3, T4, T5, T6, T7> cloneQueryable() {
        return new EasyKtQueryable7<>(entityQueryable7.cloneQueryable());
    }
}