package com.easy.query.api4j.update.impl;

import com.easy.query.api4j.update.abstraction.AbstractEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;

/**
 * @author xuejiaming
 * @FileName: EasyEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:20
 */
public class EasyEntityUpdatable<T> extends AbstractEntityUpdatable<T> {

    public EasyEntityUpdatable(ClientEntityUpdatable<T> entityObjectUpdatable) {
        super(entityObjectUpdatable);
    }
}
