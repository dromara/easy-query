package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SQLAnonymousEntityQueryExpressionBuilder;
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

    private final String cteTableName;
    private final EntityQueryExpressionBuilder sqlAnonymousUnionEntityQueryExpressionBuilder;

    public AnonymousTreeCTEQueryExpressionBuilder(String cteTableName,EntityQueryExpressionBuilder sqlAnonymousUnionEntityQueryExpressionBuilder, ExpressionContext queryExpressionContext, Class<?> queryClass) {
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
        return new AnonymousTreeCTEQuerySQLExpressionImpl(this.cteTableName,entitySQLExpressionMetadata,entityQuerySQLExpression);
    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {
        return new AnonymousTreeCTEQueryExpressionBuilder(this.cteTableName,this.sqlAnonymousUnionEntityQueryExpressionBuilder,expressionContext,queryClass);
    }
}
