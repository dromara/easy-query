package com.easy.query.api.proxy.extension.casewhen;

import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.extension.casewhen.CaseWhenBuilderExpression;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;

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
    public CaseWhenEntityBuilder then(SQLColumn<?,?> thenSQLColumn){
        caseWhenBuilderExpression.caseWhen(filter->{
            entitySQLContext._where(filter, sqlActionExpression);
        },thenSQLColumn);
        return caseWhenEntityBuilder;
    }
//    public <TProperty> CaseWhenEntityBuilder<TRProxy,TR> caseWhen(SQLActionExpression sqlActionExpression, PropTypeColumn<TProperty> then){
//        caseWhenBuilder.caseWhen(filter->{
//            entitySQLContext._where(filter, sqlActionExpression);
//        },then);
//        return this;
//    }
}