package com.easy.query.api4kt.select.impl;

import com.easy.query.api4kt.select.KtQueryable5;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable5;

/**
 * create time 2023/8/18 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyKtQueryable5<T1, T2, T3, T4, T5> extends AbstractKtQueryable5<T1, T2, T3, T4, T5> {
    public EasyKtQueryable5(ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5) {
        super(entityQueryable5);
    }

    @Override
    public KtQueryable5<T1, T2, T3, T4, T5> cloneQueryable() {
        return new EasyKtQueryable5<>(entityQueryable5.cloneQueryable());
    }
}
