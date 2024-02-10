package com.easy.query.core.expression.sql.builder.internal;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.RelationEntityTableAvailable;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasyMapUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/3/6 08:58
 */
public abstract class AbstractEntityExpressionBuilder implements EntityExpressionBuilder {
    protected final ExpressionContext expressionContext;
    protected final QueryRuntimeContext runtimeContext;
    protected final Class<?> queryClass;
    protected final List<EntityTableExpressionBuilder> tables;
    protected Map<RelationTableKey, EntityTableExpressionBuilder> relationTables;

    public AbstractEntityExpressionBuilder(ExpressionContext expressionContext, Class<?> queryClass) {
        this.expressionContext = expressionContext;
        this.runtimeContext = expressionContext.getRuntimeContext();
        this.queryClass = queryClass;
        this.tables = new ArrayList<>();
    }

    @Override
    public Class<?> getQueryClass() {
        return queryClass;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return expressionContext.getRuntimeContext();
    }

    @Override
    public void addSQLEntityTableExpression(EntityTableExpressionBuilder tableExpression) {
        if (tableExpression.getEntityTable().getEntityMetadata().isSharding()) {
            expressionContext.useSharding();
        }
        expressionContext.getTableContext().addTable(tableExpression.getEntityTable());
        tables.add(tableExpression);
    }

    @Override
    public EntityTableExpressionBuilder addRelationEntityTableExpression(RelationTableKey relationTableKey, Function<RelationTableKey, EntityTableExpressionBuilder> tableExpressionSupplier) {
        if (relationTables == null) {
            relationTables = new LinkedHashMap<>();
        }
       return  EasyMapUtil.computeIfAbsent(relationTables, relationTableKey, k -> {
            EntityTableExpressionBuilder tableExpression = tableExpressionSupplier.apply(k);

            if (tableExpression.getEntityTable().getEntityMetadata().isSharding()) {
                expressionContext.useSharding();
            }
            expressionContext.getTableContext().addTable(tableExpression.getEntityTable());
            if (tableExpression.getEntityTable() instanceof RelationEntityTableAvailable) {
                return tableExpression;
            } else {
                throw new UnsupportedOperationException();
            }
        });

    }

    @Override
    public List<EntityTableExpressionBuilder> getTables() {
        return tables;
    }

    @Override
    public Map<RelationTableKey,EntityTableExpressionBuilder> getRelationTables() {
        if (relationTables == null) {
            relationTables=new LinkedHashMap<>();
        }
        return relationTables;
    }

    @Override
    public boolean hasRelationTables() {
        return relationTables!=null&&!relationTables.isEmpty();
    }

    @Override
    public void setLogicDelete(boolean logicDelete) {
        if (logicDelete) {
            expressionContext.getBehavior().addBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        } else {
            expressionContext.getBehavior().removeBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        }
    }
}
