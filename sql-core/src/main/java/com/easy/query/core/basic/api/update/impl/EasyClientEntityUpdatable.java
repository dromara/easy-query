package com.easy.query.core.basic.api.update.impl;

import com.easy.query.core.basic.api.update.abstraction.AbstractClientEntityUpdatable;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: EasyEntityUpdate.java
 * @Description: 文件说明
 * create time 2023/3/6 12:20
 */
public class EasyClientEntityUpdatable<T> extends AbstractClientEntityUpdatable<T> {

    public EasyClientEntityUpdatable(Class<T> clazz,Collection<T> entities, EntityUpdateExpressionBuilder entityUpdateExpression) {
        super(clazz,entities, entityUpdateExpression);
    }
}
