package com.easy.query.core.proxy.impl;

import com.easy.query.core.proxy.SQLConstantExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2024/1/10 13:22
 * 文件说明
 *
 * @author xuejiaming
 */
@Deprecated
public class SQLConstantExpressionImpl implements SQLConstantExpression {


    private final EntitySQLContext entitySQLContext;

    public SQLConstantExpressionImpl(EntitySQLContext entitySQLContext) {
        this.entitySQLContext = entitySQLContext;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }
}
