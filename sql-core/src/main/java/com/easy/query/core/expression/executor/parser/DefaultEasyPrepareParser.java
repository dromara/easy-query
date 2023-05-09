package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
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

    private Set<TableAvailable> getShardingTable(EntityExpressionBuilder entityExpressionBuilder) {
        Set<TableAvailable> shardingTables = new LinkedHashSet<>(entityExpressionBuilder.getTables().size());
        for (EntityTableExpressionBuilder table : entityExpressionBuilder.getTables()) {
            if(table instanceof AnonymousEntityTableExpressionBuilder){
                getAnonymousTable((AnonymousEntityTableExpressionBuilder)table,shardingTables);
            }else{
                if (!table.tableNameIsAs() && table.getEntityMetadata().isSharding()) {
                    shardingTables.add(table.getEntityTable());
                }
            }
        }
        return shardingTables;
    }
    private void getAnonymousTable(AnonymousEntityTableExpressionBuilder anonymousEntityTableExpressionBuilder,Set<TableAvailable> shardingEntities){
        EntityQueryExpressionBuilder entityQueryExpressionBuilder = anonymousEntityTableExpressionBuilder.getEntityQueryExpressionBuilder();
        for (EntityTableExpressionBuilder table : entityQueryExpressionBuilder.getTables()) {
            if(table instanceof  AnonymousEntityTableExpressionBuilder){
                getAnonymousTable((AnonymousEntityTableExpressionBuilder)table,shardingEntities);
            }else{
                if (!table.tableNameIsAs() && table.getEntityMetadata().isSharding()) {
                    shardingEntities.add(table.getEntityTable());
                }
            }
        }
    }

    @Override
    public PrepareParseResult parse(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<Object> entities, boolean fillAutoIncrement) {
        Set<TableAvailable> shardingTables = getShardingTable(entityExpressionBuilder);
        if (entityExpressionBuilder instanceof EntityQueryExpressionBuilder) {
            return queryParseResult(executorContext,shardingTables, (EntityQueryExpressionBuilder) entityExpressionBuilder);
        }
        if (entityExpressionBuilder instanceof EntityInsertExpressionBuilder) {
            return insertParseResult(shardingTables, (EntityInsertExpressionBuilder) entityExpressionBuilder, entities, fillAutoIncrement);
        }
        if (entities == null) {
            if (entityExpressionBuilder instanceof EntityPredicateExpressionBuilder) {
                return predicatePrepareParseResult(shardingTables, (EntityPredicateExpressionBuilder) entityExpressionBuilder);
            }
        } if (entities != null) {
            return entityParseResult(shardingTables, entityExpressionBuilder, entities);
        }
        throw new NotImplementedException();
    }

    private QueryPrepareParseResult queryParseResult(ExecutorContext executorContext,Set<TableAvailable> shardingEntities, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        return new EasyQueryPrepareParseResult(executorContext,shardingEntities, entityQueryExpressionBuilder);
    }

    private InsertPrepareParseResult insertParseResult(Set<TableAvailable> shardingTables, EntityInsertExpressionBuilder entityInsertExpressionBuilder, List<Object> entities, boolean fillAutoIncrement) {
        return new EasyInsertPrepareParseResult(shardingTables, entityInsertExpressionBuilder, entities, fillAutoIncrement);
    }

    private EntityPrepareParseResult entityParseResult(Set<TableAvailable> shardingTables, EntityExpressionBuilder entityExpressionBuilder, List<Object> entities) {
        return new EasyEntityPrepareParseResult(shardingTables, entityExpressionBuilder, entities);
    }

    private EasyPredicatePrepareParseResult predicatePrepareParseResult(Set<TableAvailable> shardingTables, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder) {
        return new EasyPredicatePrepareParseResult(shardingTables, entityPredicateExpressionBuilder);
    }


}
