package com.easy.query.api4j.delete.impl;

import com.easy.query.api4j.delete.abstraction.AbstractExpressionDeletable;
import com.easy.query.core.basic.api.delete.ExpressionObjectDeletable;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:21
 */
public class EasyExpressionDeletable<T> extends AbstractExpressionDeletable<T> {
    public EasyExpressionDeletable(ExpressionObjectDeletable<T> expressionObjectDeletable) {
        super(expressionObjectDeletable);
    }
}
