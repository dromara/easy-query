package com.easy.query.api.proxy.extension.casewhen;

import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.segment.scec.expression.SQLSegmentParamExpressionImpl;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.extension.casewhen.CaseWhenBuilderExpression;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2024/2/27 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenThenEntityBuilder {

    private final SQLActionExpression sqlActionExpression;
    private final CaseWhenEntityBuilder caseWhenEntityBuilder;
    private final EntitySQLContext entitySQLContext;
    private final CaseWhenBuilderExpression caseWhenBuilderExpression;

    public CaseWhenThenEntityBuilder(CaseWhenEntityBuilder caseWhenEntityBuilder, EntitySQLContext entitySQLContext, CaseWhenBuilderExpression caseWhenBuilderExpression, SQLActionExpression sqlActionExpression){
        this.caseWhenEntityBuilder = caseWhenEntityBuilder;
        this.entitySQLContext = entitySQLContext;
        this.caseWhenBuilderExpression = caseWhenBuilderExpression;
        this.sqlActionExpression = sqlActionExpression;
    }
    public CaseWhenEntityBuilder then(Object then){
        caseWhenBuilderExpression.caseWhen(filter->{
            entitySQLContext._where(filter, sqlActionExpression);
        },then);
        return caseWhenEntityBuilder;
    }
    public <T extends SQLTableOwner & DSLSQLFunctionAvailable> CaseWhenEntityBuilder then(T then){
        SQLFunction sqlFunction = then.func().apply(entitySQLContext.getRuntimeContext().fx());
        ExpressionContext expressionContext = entitySQLContext.getEntityExpressionBuilder().getExpressionContext();
        SQLSegmentParamExpressionImpl sqlSegmentParamExpression = new SQLSegmentParamExpressionImpl(sqlFunction, expressionContext, then.getTable(), expressionContext.getRuntimeContext(), null);
//        new
        caseWhenBuilderExpression.caseWhen(filter->{
            entitySQLContext._where(filter, sqlActionExpression);
        },sqlSegmentParamExpression);
        return caseWhenEntityBuilder;
    }
    public CaseWhenEntityBuilder then(SQLColumn<?,?> thenSQLColumn){
        caseWhenBuilderExpression.caseWhenColumn(filter->{
            entitySQLContext._where(filter, sqlActionExpression);
        },thenSQLColumn.getTable(),thenSQLColumn.getValue());
        return caseWhenEntityBuilder;
    }
//    public <TProperty> CaseWhenEntityBuilder<TRProxy,TR> caseWhen(SQLActionExpression sqlActionExpression, PropTypeColumn<TProperty> then){
//        caseWhenBuilder.caseWhen(filter->{
//            entitySQLContext._where(filter, sqlActionExpression);
//        },then);
//        return this;
//    }
}