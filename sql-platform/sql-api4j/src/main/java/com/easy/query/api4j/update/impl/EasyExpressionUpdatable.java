package com.easy.query.api4j.update.impl;

import com.easy.query.api4j.update.abstraction.AbstractExpressionUpdatable;
import com.easy.query.core.basic.api.update.ExpressionObjectUpdatable;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:21
 */
public class EasyExpressionUpdatable<T> extends AbstractExpressionUpdatable<T> {
    public EasyExpressionUpdatable(ExpressionObjectUpdatable<T> expressionObjectUpdatable) {
        super(expressionObjectUpdatable);
    }
}
