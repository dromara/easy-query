package com.easy.query.api4j.update.impl;

import com.easy.query.api4j.update.abstraction.AbstractEntityUpdatable;
import com.easy.query.core.basic.api.update.EntityObjectUpdatable;

/**
 * @author xuejiaming
 * @FileName: EasyEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:20
 */
public class EasyEntityUpdatable<T> extends AbstractEntityUpdatable<T> {

    public EasyEntityUpdatable(EntityObjectUpdatable<T> entityObjectUpdatable) {
        super(entityObjectUpdatable);
    }
}
