package com.easy.query.core.proxy.core.flat.casewhen;

import com.easy.query.api.proxy.util.EasyParamExpressionUtil;
import com.easy.query.core.extension.casewhen.CaseWhenBuilderExpression;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilder;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2024/2/27 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenThenEntityBuilder {

    private final FlatElementCaseWhenEntityBuilder caseWhenEntityBuilder;
    private final EntitySQLContext entitySQLContext;
    private final SQLCaseWhenBuilder sqlCaseWhenBuilder;
    private final SQLPredicateExpression sqlPredicateExpression;

    public CaseWhenThenEntityBuilder(FlatElementCaseWhenEntityBuilder caseWhenEntityBuilder, EntitySQLContext entitySQLContext, SQLCaseWhenBuilder sqlCaseWhenBuilder, SQLPredicateExpression sqlPredicateExpression) {
        this.caseWhenEntityBuilder = caseWhenEntityBuilder;
        this.entitySQLContext = entitySQLContext;
        this.sqlCaseWhenBuilder = sqlCaseWhenBuilder;
        this.sqlPredicateExpression = sqlPredicateExpression;
    }

    public <TV> FlatElementCaseWhenEntityBuilder then(TV then) {
        sqlCaseWhenBuilder.caseWhen(filter -> {
            sqlPredicateExpression.accept(filter);
        }, EasyParamExpressionUtil.getParamExpression(entitySQLContext,then));
        return caseWhenEntityBuilder;
    }

    //
//        if(column instanceof SQLColumn){
//        selector.expression((SQLColumn)column);
//    }else if(column instanceof Query){
//        selector.expression((Query)column);
//    }else if(column instanceof DSLSQLFunctionAvailable){
//        selector.expression((DSLSQLFunctionAvailable)column);
//    }else if(column instanceof SQLFunction){
//        selector.expression((SQLFunction)column);
//    }else {
//        throw new UnsupportedOperationException();
//    }
//    public <T extends SQLTableOwner & DSLSQLFunctionAvailable> CaseWhenEntityBuilder then(T then) {
//        SQLFunction sqlFunction = then.func().apply(entitySQLContext.getRuntimeContext().fx());
//        ExpressionContext expressionContext = entitySQLContext.getEntityExpressionBuilder().getExpressionContext();
//        SQLSegmentParamExpressionImpl sqlSegmentParamExpression = new SQLSegmentParamExpressionImpl(sqlFunction, expressionContext, then.getTable(), expressionContext.getRuntimeContext(), null);
////        new
//        caseWhenBuilderExpression.caseWhen(filter -> {
//            entitySQLContext._where(filter, sqlActionExpression);
//        }, sqlSegmentParamExpression);
//        return caseWhenEntityBuilder;
//    }
//    public <TProperty> CaseWhenEntityBuilder<TRProxy,TR> caseWhen(SQLActionExpression sqlActionExpression, PropTypeColumn<TProperty> then){
//        caseWhenBuilder.caseWhen(filter->{
//            entitySQLContext._where(filter, sqlActionExpression);
//        },then);
//        return this;
//    }
}