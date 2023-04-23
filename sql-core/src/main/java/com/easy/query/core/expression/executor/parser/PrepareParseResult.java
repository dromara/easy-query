package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;

import java.util.Set;

/**
 * create time 2023/4/9 22:18
 * 文件说明
 *
 * @author xuejiaming
 */
public final class PrepareParseResult {
    private final Set<Class<?>> shardingEntities;
    private final EasyEntitySqlExpression easyEntitySqlExpression;

    public PrepareParseResult(Set<Class<?>> shardingEntities, EasyEntitySqlExpression easyEntitySqlExpression){

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
