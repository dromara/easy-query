package com.easy.query.api4j.select.impl;

import com.easy.query.api4j.select.Queryable8;
import com.easy.query.api4j.select.abstraction.AbstractQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable8;

/**
 * create time 2023/8/18 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> extends AbstractQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> {
    public EasyQueryable8(ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8) {
        super(entityQueryable8);
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> cloneQueryable() {
        return new EasyQueryable8<>(entityQueryable8.cloneQueryable());
    }
}