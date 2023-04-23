package com.easy.query.core.sharding.route.table.engine;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.sharding.route.datasource.engine.DataSourceRouteResult;

import java.util.Set;

/**
 * create time 2023/4/5 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public final class TableRouteContext {
    private final DataSourceRouteResult dataSourceRouteResult;
    private final EasyEntitySqlExpression easyEntitySqlExpression;
    private final Set<Class<?>> shardingEntities;

    public TableRouteContext(DataSourceRouteResult dataSourceRouteResult, EasyEntitySqlExpression easyEntitySqlExpression, Set<Class<?>> shardingEntities){

        this.dataSourceRouteResult = dataSourceRouteResult;
        this.easyEntitySqlExpression = easyEntitySqlExpression;
        this.shardingEntities = shardingEntities;
    }

    public DataSourceRouteResult getDataSourceRouteResult() {
        return dataSourceRouteResult;
    }

    public EasyEntitySqlExpression getEntitySqlExpression() {
        return easyEntitySqlExpression;
    }

    public Set<Class<?>> getShardingEntities() {
        return shardingEntities;
    }
}
