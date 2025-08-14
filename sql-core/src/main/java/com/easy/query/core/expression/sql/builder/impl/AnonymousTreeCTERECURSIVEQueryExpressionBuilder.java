package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SQLAnonymousEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;

/**
 * create time 2023/3/31 10:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousTreeCTERECURSIVEQueryExpressionBuilder extends QueryExpressionBuilder implements SQLAnonymousEntityQueryExpressionBuilder {

    private final String cteTableName;
    private final EntityQueryExpressionBuilder sqlAnonymousUnionEntityQueryExpressionBuilder;

    public AnonymousTreeCTERECURSIVEQueryExpressionBuilder(String cteTableName, EntityQueryExpressionBuilder sqlAnonymousUnionEntityQueryExpressionBuilder, ExpressionContext queryExpressionContext, Class<?> queryClass) {
        super(queryExpressionContext, queryClass);
        this.cteTableName = cteTableName;
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
        return runtimeContext.getExpressionFactory().createEasyAnonymousCTEQuerySQLExpression(this.cteTableName,entitySQLExpressionMetadata,entityQuerySQLExpression);
    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {
        return runtimeContext.getExpressionBuilderFactory().createAnonymousCTEQueryExpressionBuilder(this.cteTableName,this.sqlAnonymousUnionEntityQueryExpressionBuilder,expressionContext,queryClass);
    }


    public String getCteTableName() {
        return cteTableName;
    }
}
