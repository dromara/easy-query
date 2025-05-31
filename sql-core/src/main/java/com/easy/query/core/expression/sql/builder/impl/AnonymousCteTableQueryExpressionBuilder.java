package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.basic.api.cte.CteTableAvailable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SQLAnonymousEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;

/**
 * create time 2023/3/31 10:59
 * cte 公共表达式
 * [eg. with table as (...)]
 *
 * @author xuejiaming
 */
public class AnonymousCteTableQueryExpressionBuilder extends QueryExpressionBuilder implements SQLAnonymousEntityQueryExpressionBuilder, CteTableAvailable {

    private final String withTableName;
    private final EntityQueryExpressionBuilder sqlAnonymousUnionEntityQueryExpressionBuilder;

    public AnonymousCteTableQueryExpressionBuilder(String withTableName, EntityQueryExpressionBuilder sqlAnonymousUnionEntityQueryExpressionBuilder, ExpressionContext queryExpressionContext, Class<?> queryClass) {
        super(queryExpressionContext, queryClass);
        this.withTableName = withTableName;
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
        return runtimeContext.getExpressionFactory().createEasyAnonymousWithTableQuerySQLExpression(this.withTableName,entitySQLExpressionMetadata,entityQuerySQLExpression);
    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {
        return runtimeContext.getExpressionBuilderFactory().createAnonymousWithTableQueryExpressionBuilder(this.withTableName,this.sqlAnonymousUnionEntityQueryExpressionBuilder,expressionContext,queryClass);
    }


    @Override
    public String getCteTableName() {
        return withTableName;
    }

    @Override
    public Class<?> cteTableClass() {
        return queryClass;
    }
}
