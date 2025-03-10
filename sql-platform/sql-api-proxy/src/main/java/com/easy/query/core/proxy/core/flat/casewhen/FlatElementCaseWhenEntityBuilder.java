package com.easy.query.core.proxy.core.flat.casewhen;

import com.easy.query.api.proxy.util.EasyParamExpressionUtil;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.extension.casewhen.CaseWhenBuilderExpression;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

/**
 * create time 2024/2/27 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class FlatElementCaseWhenEntityBuilder {
    private final CaseWhenBuilderExpression caseWhenBuilder;
    private final EntitySQLContext entitySQLContext;

    public FlatElementCaseWhenEntityBuilder(EntitySQLContext entitySQLContext){
        this.entitySQLContext = entitySQLContext;
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        Objects.requireNonNull(entityExpressionBuilder,"CaseWhenEntityBuilder entitySQLContext.getEntityExpressionBuilder() is null");
        this.caseWhenBuilder=new CaseWhenBuilderExpression(entitySQLContext.getRuntimeContext(),entityExpressionBuilder.getExpressionContext());
    }
    public CaseWhenThenEntityBuilder caseWhen(SQLPredicateExpression sqlPredicateExpression){
        return new CaseWhenThenEntityBuilder(this,entitySQLContext,caseWhenBuilder,sqlPredicateExpression);
    }
//    public <TProperty> CaseWhenEntityBuilder<TRProxy,TR> caseWhen(SQLActionExpression sqlActionExpression, PropTypeColumn<TProperty> then){
//        caseWhenBuilder.caseWhen(filter->{
//            entitySQLContext._where(filter, sqlActionExpression);
//        },then);
//        return this;
//    }
    public <TV,TProperty> ColumnFunctionCompareComparableAnyChainExpression<TProperty> elseEnd(TV elseValue){
        return EasyObjectUtil.typeCastNullable(elseEnd(elseValue,Object.class));
    }
    public <TV,TProperty> ColumnFunctionCompareComparableAnyChainExpression<TProperty> elseEnd(TV elseValue,Class<TProperty> resultClass){
        ParamExpression paramExpression = EasyParamExpressionUtil.getParamExpression(entitySQLContext, elseValue);
        SQLFunction sqlFunction = caseWhenBuilder.elseEnd(paramExpression);
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(entitySQLContext,null,null, f->sqlFunction);
    }
}