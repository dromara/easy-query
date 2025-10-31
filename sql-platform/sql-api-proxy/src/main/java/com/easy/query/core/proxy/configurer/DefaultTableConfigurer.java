package com.easy.query.core.proxy.configurer;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.core.EntitySQLContext;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2025/7/30 11:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultTableConfigurer<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements TableConfigurer<TProxy, TEntity> {
    private final EntitySQLContext entitySQLContext;
    private final TProxy tProxy;
    private final EntityTableExpressionBuilder tableExpressionBuilder;

    public DefaultTableConfigurer(EntitySQLContext entitySQLContext, TProxy tProxy) {
        this.entitySQLContext = entitySQLContext;
        this.tProxy = tProxy;
        this.tableExpressionBuilder=getTableExpressionBuilder();
        if(tableExpressionBuilder==null){
            throw new EasyQueryInvalidOperationException("Table information not found in the expression context.");
        }
    }

    private EntityTableExpressionBuilder getTableExpressionBuilder() {
        TableAvailable table = tProxy.getTable();
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        List<EntityTableExpressionBuilder> tables = entityExpressionBuilder.getTables();
        for (EntityTableExpressionBuilder value : tables) {
            if (value.getEntityTable() == table) {
                return value;
            }
        }
        Map<RelationTableKey, EntityTableExpressionBuilder> relationTables = entityExpressionBuilder.getRelationTables();
        for (Map.Entry<RelationTableKey, EntityTableExpressionBuilder> rtkv : relationTables.entrySet()) {

            EntityTableExpressionBuilder value = rtkv.getValue();
            if (rtkv.getValue().getEntityTable() == table) {
                return value;
            }
        }
        return null;
    }


    @Override
    public TableConfigurer<TProxy, TEntity> useLogicDelete(boolean use) {
        tableExpressionBuilder.setTableLogicDelete(() -> use);

        return this;
    }


    @Override
    public TableConfigurer<TProxy, TEntity> asTable(Function<String, String> tableNameAs) {
        tableExpressionBuilder.setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public TableConfigurer<TProxy, TEntity> asAlias(String alias) {
        tableExpressionBuilder.asAlias(alias);
        return this;
    }

    @Override
    public TableConfigurer<TProxy, TEntity> asTableSegment(BiFunction<String, String, String> segmentAs) {
        tableExpressionBuilder.setTableSegmentAs(segmentAs);
        return this;
    }
}
