package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SQLAnonymousEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.AnonymousEntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousTreeCTEQuerySQLExpressionImpl;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;

/**
 * create time 2023/3/31 10:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousTreeCTEQueryExpressionBuilder extends QueryExpressionBuilder implements SQLAnonymousEntityQueryExpressionBuilder {

    private final EntityQueryExpressionBuilder sqlAnonymousUnionEntityQueryExpressionBuilder;

    public AnonymousTreeCTEQueryExpressionBuilder(EntityQueryExpressionBuilder sqlAnonymousUnionEntityQueryExpressionBuilder, ExpressionContext queryExpressionContext, Class<?> queryClass) {
        super(queryExpressionContext, queryClass);
        this.sqlAnonymousUnionEntityQueryExpressionBuilder = sqlAnonymousUnionEntityQueryExpressionBuilder;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public EntityQuerySQLExpression toExpression() {
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        EntityQuerySQLExpression entityQuerySQLExpression = sqlAnonymousUnionEntityQueryExpressionBuilder.toExpression();
        return new AnonymousTreeCTEQuerySQLExpressionImpl(entitySQLExpressionMetadata,entityQuerySQLExpression);
    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {
        return new AnonymousTreeCTEQueryExpressionBuilder(this.sqlAnonymousUnionEntityQueryExpressionBuilder,expressionContext,queryClass);
    }
}
