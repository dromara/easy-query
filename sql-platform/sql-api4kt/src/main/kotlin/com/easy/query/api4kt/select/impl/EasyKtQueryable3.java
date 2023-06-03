package com.easy.query.api4kt.select.impl;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable3;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 */
public class EasyKtQueryable3<T1, T2, T3> extends AbstractKtQueryable3<T1, T2, T3> {
    public EasyKtQueryable3(ClientQueryable3<T1, T2, T3> entityQueryable3) {
        super(entityQueryable3);
    }

    @Override
    public KtQueryable<T1> cloneQueryable() {
        return new EasyKtQueryable3<>(entityQueryable3);
    }
}
