package com.easy.query.api4j.select.impl;

import com.easy.query.api4j.select.Queryable5;
import com.easy.query.api4j.select.abstraction.AbstractQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable5;

/**
 * create time 2023/8/18 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryable5<T1, T2, T3, T4, T5> extends AbstractQueryable5<T1, T2, T3, T4, T5> {
    public EasyQueryable5(ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5) {
        super(entityQueryable5);
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> cloneQueryable() {
        return new EasyQueryable5<>(entityQueryable5.cloneQueryable());
    }
}
