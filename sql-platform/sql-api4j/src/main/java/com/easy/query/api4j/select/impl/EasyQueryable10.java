package com.easy.query.api4j.select.impl;

import com.easy.query.api4j.select.Queryable10;
import com.easy.query.api4j.select.abstraction.AbstractQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable10;

/**
 * create time 2023/8/18 13:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends AbstractQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
    public EasyQueryable10(ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10) {
        super(entityQueryable10);
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> cloneQueryable() {
        return new EasyQueryable10<>(entityQueryable10);
    }
}