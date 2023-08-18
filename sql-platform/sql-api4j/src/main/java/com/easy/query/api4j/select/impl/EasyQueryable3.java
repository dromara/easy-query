package com.easy.query.api4j.select.impl;

import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.abstraction.AbstractQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable3;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:30
 */
public class EasyQueryable3<T1, T2, T3> extends AbstractQueryable3<T1, T2, T3> {
    public EasyQueryable3(ClientQueryable3<T1, T2, T3> entityQueryable3) {
        super(entityQueryable3);
    }

    @Override
    public Queryable3<T1,T2,T3> cloneQueryable() {
        return new EasyQueryable3<>(entityQueryable3.cloneQueryable());
    }
}
