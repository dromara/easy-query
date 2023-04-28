package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * create time 2023/4/9 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyPrepareParser implements EasyPrepareParser {

    private Set<Class<?>> getShardingEntities(EntityExpressionBuilder entityExpressionBuilder) {
        Set<Class<?>> shardingEntities = new LinkedHashSet<>(entityExpressionBuilder.getTables().size());
        for (EntityTableExpressionBuilder table : entityExpressionBuilder.getTables()) {
            if (!table.tableNameIsAs() && table.getEntityMetadata().isSharding()) {
                shardingEntities.add(table.getEntityMetadata().getEntityClass());
            }
        }
        return shardingEntities;
    }

    @Override
    public PrepareParseResult parse(EntityExpressionBuilder entityExpressionBuilder, List<Object> entities, boolean fillAutoIncrement) {
        Set<Class<?>> shardingEntities = getShardingEntities(entityExpressionBuilder);
        if (entityExpressionBuilder instanceof EntityQueryExpressionBuilder) {
            return queryParseResult(shardingEntities, (EntityQueryExpressionBuilder) entityExpressionBuilder);
        }
        if (entityExpressionBuilder instanceof EntityInsertExpressionBuilder) {
            return insertParseResult(shardingEntities, (EntityInsertExpressionBuilder) entityExpressionBuilder, entities, fillAutoIncrement);
        }
        if (entities == null) {
            if (entityExpressionBuilder instanceof EntityPredicateExpressionBuilder) {
                return predicatePrepareParseResult(shardingEntities, (EntityPredicateExpressionBuilder) entityExpressionBuilder);
            }
        } if (entities != null) {
            return entityParseResult(shardingEntities, entityExpressionBuilder, entities);
        }
        throw new NotImplementedException();
    }

    private QueryPrepareParseResult queryParseResult(Set<Class<?>> shardingEntities, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        return new EasyQueryPrepareParseResult(shardingEntities, entityQueryExpressionBuilder);
    }

    private InsertPrepareParseResult insertParseResult(Set<Class<?>> shardingEntities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, List<Object> entities, boolean fillAutoIncrement) {
        return new EasyInsertPrepareParseResult(shardingEntities, entityInsertExpressionBuilder, entities, fillAutoIncrement);
    }

    private EntityPrepareParseResult entityParseResult(Set<Class<?>> shardingEntities, EntityExpressionBuilder entityExpressionBuilder, List<Object> entities) {
        return new EasyEntityPrepareParseResult(shardingEntities, entityExpressionBuilder, entities);
    }

    private EasyPredicatePrepareParseResult predicatePrepareParseResult(Set<Class<?>> shardingEntities, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder) {
        return new EasyPredicatePrepareParseResult(shardingEntities, entityPredicateExpressionBuilder);
    }


}
