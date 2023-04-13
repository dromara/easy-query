package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityInsertExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.EntityUpdateExpression;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * create time 2023/4/9 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyPrepareParser implements EasyPrepareParser {
    @Override
    public PrepareParseResult parse(EntityExpression entityExpression) {
        Set<Class<?>> shardingEntities = getShardingEntities(entityExpression);
        return new PrepareParseResult(shardingEntities, entityExpression);
    }


    private Set<Class<?>> getShardingEntities(EntityExpression entityExpression) {
        Set<Class<?>> shardingEntities = new HashSet<>(entityExpression.getTables().size());
        for (EntityTableExpression table : entityExpression.getTables()) {
            if (!table.tableNameIsAs() && table.getEntityMetadata().isSharding()) {
                shardingEntities.add(table.getEntityClass());
            }
        }
        return shardingEntities;
    }
}
