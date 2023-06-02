package com.easy.query.api4j.select.impl;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.abstraction.AbstractQueryable4;
import com.easy.query.core.basic.api.select.ObjectQueryable4;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 */
public class EasyQueryable4<T1, T2, T3, T4> extends AbstractQueryable4<T1, T2, T3, T4> {
    public EasyQueryable4(ObjectQueryable4<T1, T2, T3, T4> entityQueryable4) {
        super(entityQueryable4);
    }

    @Override
    public Queryable<T1> cloneQueryable() {
        return new EasyQueryable<>(entityQueryable4);
    }
}
