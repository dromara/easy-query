package com.easy.query.api4kt.select.abstraction;

import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable1.java
 * @Description: 文件说明
 * @Date: 2023/3/4 14:12
 */
public abstract class AbstractKtQueryable1<T> extends AbstractKtQueryable<T> {
    public AbstractKtQueryable1(ClientQueryable<T> entityQueryable) {
        super(entityQueryable);
    }

}
