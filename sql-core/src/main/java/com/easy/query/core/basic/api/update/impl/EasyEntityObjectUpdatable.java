package com.easy.query.core.basic.api.update.impl;

import com.easy.query.core.basic.api.update.abstraction.AbstractEntityObjectUpdatable;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: EasyEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:20
 */
public class EasyEntityObjectUpdatable<T> extends AbstractEntityObjectUpdatable<T> {

    public EasyEntityObjectUpdatable(Collection<T> entities, EntityUpdateExpressionBuilder entityUpdateExpression) {
        super(entities, entityUpdateExpression);
    }
}
