package com.easy.query.api4kt.delete.impl;

import com.easy.query.api4kt.delete.abstraction.AbstractKtExpressionDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:21
 */
public class EasyKtExpressionDeletable<T> extends AbstractKtExpressionDeletable<T> {
    public EasyKtExpressionDeletable(ClientExpressionDeletable<T> expressionObjectDeletable) {
        super(expressionObjectDeletable);
    }
}
