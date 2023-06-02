package com.easy.query.api4j.delete.impl;

import com.easy.query.api4j.delete.abstraction.AbstractEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:20
 */
public class EasyEntityDeletable<T> extends AbstractEntityDeletable<T> {
    public EasyEntityDeletable(ClientEntityDeletable<T> entityObjectDeletable) {
        super(entityObjectDeletable);
    }
}
