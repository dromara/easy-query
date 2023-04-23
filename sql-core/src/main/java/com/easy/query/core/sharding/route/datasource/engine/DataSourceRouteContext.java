package com.easy.query.core.sharding.route.datasource.engine;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;

import java.util.Set;

/**
 * create time 2023/4/11 13:11
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DataSourceRouteContext {
    private final Set<Class<?>> shardingEntities;
    private final EasyEntitySqlExpression easyEntitySqlExpression;

    public DataSourceRouteContext(Set<Class<?>> shardingEntities, EasyEntitySqlExpression easyEntitySqlExpression){

        this.shardingEntities = shardingEntities;
        this.easyEntitySqlExpression = easyEntitySqlExpression;
    }

    public Set<Class<?>> getShardingEntities() {
        return shardingEntities;
    }

    public EasyEntitySqlExpression getEntitySqlExpression() {
        return easyEntitySqlExpression;
    }
}
