package com.easy.query.core.proxy;

import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2024/1/10 13:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLConstantValueAvailableImpl implements SQLConstantValueAvailable {


    private final EntitySQLContext entitySQLContext;

    public SQLConstantValueAvailableImpl(EntitySQLContext entitySQLContext) {
        this.entitySQLContext = entitySQLContext;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }
}
