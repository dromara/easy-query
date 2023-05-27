package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.sql.TableContext;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SQLAnonymousUnionEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/3/31 10:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousUnionQueryExpressionBuilder extends QueryExpressionBuilder implements SQLAnonymousUnionEntityQueryExpressionBuilder {

    private final List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders;
    private final SQLUnionEnum sqlUnion;

    public AnonymousUnionQueryExpressionBuilder(List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders, ExpressionContext queryExpressionContext, SQLUnionEnum sqlUnion) {
        super(queryExpressionContext);
        this.entityQueryExpressionBuilders = entityQueryExpressionBuilders;
        this.sqlUnion = sqlUnion;
    }

    @Override
    public List<EntityQueryExpressionBuilder> getEntityQueryExpressionBuilders() {
        return entityQueryExpressionBuilders;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public EntityQuerySQLExpression toExpression() {
       List<EntityQuerySQLExpression> querySQLExpressions = new ArrayList<>(entityQueryExpressionBuilders.size());
        TableContext tableContext = new TableContext();
        for (EntityQueryExpressionBuilder entityQueryExpressionBuilder : entityQueryExpressionBuilders) {
            tableContext.extract(entityQueryExpressionBuilder.getExpressionContext().getTableContext());
            EntityQuerySQLExpression expression = entityQueryExpressionBuilder.toExpression();
            querySQLExpressions.add(expression);
        }
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(tableContext, runtimeContext);
        return runtimeContext.getExpressionFactory().createEasyAnonymousUnionQuerySQLExpression(entitySQLExpressionMetadata,new ArrayList<>(querySQLExpressions),sqlUnion);
    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {
        ArrayList<EntityQueryExpressionBuilder> entityQueryExpressionBuilderCopies = new ArrayList<>(entityQueryExpressionBuilders.size());
        for (EntityQueryExpressionBuilder entityQueryExpressionBuilder : entityQueryExpressionBuilders) {
            entityQueryExpressionBuilderCopies.add(entityQueryExpressionBuilder.cloneEntityExpressionBuilder());
        }

        EntityQueryExpressionBuilder anonymousQueryExpressionBuilder =runtimeContext.getExpressionBuilderFactory().createAnonymousUnionQueryExpressionBuilder(entityQueryExpressionBuilderCopies,expressionContext.cloneExpressionContext(),sqlUnion);

        for (EntityTableExpressionBuilder table : super.tables) {
            anonymousQueryExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        return anonymousQueryExpressionBuilder;
    }
}
