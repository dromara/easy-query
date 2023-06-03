package com.easy.query.api4kt.update.impl;

import com.easy.query.api4kt.update.abstraction.AbstractKtExpressionUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:21
 */
public class EasyKtExpressionUpdatable<T> extends AbstractKtExpressionUpdatable<T> {
    public EasyKtExpressionUpdatable(ClientExpressionUpdatable<T> expressionObjectUpdatable) {
        super(expressionObjectUpdatable);
    }
}
