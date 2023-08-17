package com.easy.query.api4j.select.impl;

import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.select.abstraction.AbstractQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable2;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 */
public class EasyQueryable2<T1, T2> extends AbstractQueryable2<T1, T2> {
    public EasyQueryable2(ClientQueryable2<T1, T2> entityQueryable2) {
        super(entityQueryable2);
    }

    @Override
    public Queryable2<T1,T2> cloneQueryable() {
        return new EasyQueryable2<>(entityQueryable2);
    }
}
