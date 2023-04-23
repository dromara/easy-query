package com.easy.query.core.basic.api.update.impl;

import com.easy.query.core.basic.api.update.abstraction.AbstractExpressionUpdatable;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

/**
 * @FileName: EasyExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:21
 * @author xuejiaming
 */
public class EasyExpressionUpdatable<T> extends AbstractExpressionUpdatable<T> {
    public EasyExpressionUpdatable(Class<T> clazz, EntityUpdateExpressionBuilder entityUpdateExpression) {
        super(clazz, entityUpdateExpression);
    }
}
