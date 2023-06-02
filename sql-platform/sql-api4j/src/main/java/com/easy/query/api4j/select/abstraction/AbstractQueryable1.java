package com.easy.query.api4j.select.abstraction;

import com.easy.query.core.basic.api.select.ObjectQueryable;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable1.java
 * @Description: 文件说明
 * @Date: 2023/3/4 14:12
 */
public abstract class AbstractQueryable1<T> extends AbstractQueryable<T> {
    public AbstractQueryable1(ObjectQueryable<T> entityQueryable) {
        super(entityQueryable);
    }

}
