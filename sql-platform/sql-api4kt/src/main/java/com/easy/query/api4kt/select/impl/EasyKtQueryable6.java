package com.easy.query.api4kt.select.impl;

import com.easy.query.api4kt.select.KtQueryable6;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable6;
import com.easy.query.core.basic.api.select.ClientQueryable6;

/**
 * create time 2023/8/18 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyKtQueryable6<T1, T2, T3, T4, T5, T6> extends AbstractKtQueryable6<T1, T2, T3, T4, T5, T6> {
    public EasyKtQueryable6(ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6) {
        super(entityQueryable6);
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> cloneQueryable() {
        return new EasyKtQueryable6<>(entityQueryable6.cloneQueryable());
    }
}
