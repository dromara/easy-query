package com.easy.query.core.basic.api.update.impl;

import com.easy.query.core.basic.api.update.abstraction.AbstractClientExpressionUpdatable;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:21
 */
public class EasyClientExpressionUpdatable<T> extends AbstractClientExpressionUpdatable<T> {
    public EasyClientExpressionUpdatable(Class<T> clazz, EntityUpdateExpressionBuilder entityUpdateExpression) {
        super(clazz, entityUpdateExpression);
    }
}
