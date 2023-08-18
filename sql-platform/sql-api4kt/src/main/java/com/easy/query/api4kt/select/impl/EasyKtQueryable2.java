package com.easy.query.api4kt.select.impl;

import com.easy.query.api4kt.select.KtQueryable2;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable2;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 */
public class EasyKtQueryable2<T1, T2> extends AbstractKtQueryable2<T1, T2> {
    public EasyKtQueryable2(ClientQueryable2<T1, T2> entityQueryable2) {
        super(entityQueryable2);
    }

    @Override
    public KtQueryable2<T1,T2> cloneQueryable() {
        return new EasyKtQueryable2<>(entityQueryable2.cloneQueryable());
    }
}
