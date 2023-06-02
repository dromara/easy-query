package com.easy.query.api4j.delete.impl;

import com.easy.query.api4j.delete.abstraction.AbstractExpressionDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:21
 */
public class EasyExpressionDeletable<T> extends AbstractExpressionDeletable<T> {
    public EasyExpressionDeletable(ClientExpressionDeletable<T> expressionObjectDeletable) {
        super(expressionObjectDeletable);
    }
}
