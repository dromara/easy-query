package com.easy.query.api4kt.select.impl;

import com.easy.query.api4kt.select.KtQueryable10;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable10;

/**
 * create time 2023/8/18 13:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyKtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends AbstractKtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
    public EasyKtQueryable10(ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10) {
        super(entityQueryable10);
    }

    @Override
    public KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> cloneQueryable() {
        return new EasyKtQueryable10<>(entityQueryable10.cloneQueryable());
    }
}