package com.easy.query.api4j.select.impl;

import com.easy.query.api4j.select.Queryable9;
import com.easy.query.api4j.select.abstraction.AbstractQueryable9;
import com.easy.query.core.basic.api.select.ClientQueryable9;

/**
 * create time 2023/8/18 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends AbstractQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
    public EasyQueryable9(ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9) {
        super(entityQueryable9);
    }

    @Override
    public Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> cloneQueryable() {
        return new EasyQueryable9<>(entityQueryable9.cloneQueryable());
    }
}
