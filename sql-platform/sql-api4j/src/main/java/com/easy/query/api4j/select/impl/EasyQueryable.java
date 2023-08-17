package com.easy.query.api4j.select.impl;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.abstraction.AbstractQueryable1;
import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * @author xuejiaming
 * @FileName: EasyQueryable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:28
 */
public class EasyQueryable<T> extends AbstractQueryable1<T> {
    public EasyQueryable(ClientQueryable<T> entityQueryable) {
        super(entityQueryable);
    }

    @Override
    public Queryable<T> cloneQueryable() {
        return new EasyQueryable<>(entityQueryable.cloneQueryable());
    }

}
