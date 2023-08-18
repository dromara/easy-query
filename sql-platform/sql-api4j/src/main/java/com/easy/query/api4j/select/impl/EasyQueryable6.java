package com.easy.query.api4j.select.impl;

import com.easy.query.api4j.select.Queryable6;
import com.easy.query.api4j.select.abstraction.AbstractQueryable6;
import com.easy.query.core.basic.api.select.ClientQueryable6;

/**
 * create time 2023/8/18 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryable6<T1, T2, T3, T4, T5, T6> extends AbstractQueryable6<T1, T2, T3, T4, T5, T6> {
    public EasyQueryable6(ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6) {
        super(entityQueryable6);
    }

    @Override
    public Queryable6<T1, T2, T3, T4, T5, T6> cloneQueryable() {
        return new EasyQueryable6<>(entityQueryable6);
    }
}
