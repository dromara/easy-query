package com.easy.query.core.basic.api.delete.impl;

import com.easy.query.core.basic.api.delete.abstraction.AbstractEntityObjectDeletable;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:20
 */
public class EasyEntityObjectDeletable<T> extends AbstractEntityObjectDeletable<T> {
    public EasyEntityObjectDeletable(Collection<T> entities, EntityDeleteExpressionBuilder entityDeleteExpression) {
        super(entities, entityDeleteExpression);
    }
}
