package com.easy.query.api4j.select.impl;

import com.easy.query.api4j.select.Queryable7;
import com.easy.query.api4j.select.abstraction.AbstractQueryable7;
import com.easy.query.core.basic.api.select.ClientQueryable7;

/**
 * create time 2023/8/18 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryable7<T1, T2, T3, T4, T5, T6, T7> extends AbstractQueryable7<T1, T2, T3, T4, T5, T6, T7> {
    public EasyQueryable7(ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7) {
        super(entityQueryable7);
    }

    @Override
    public Queryable7<T1, T2, T3, T4, T5, T6, T7> cloneQueryable() {
        return new EasyQueryable7<>(entityQueryable7.cloneQueryable());
    }
}