package com.easy.query.core.proxy.core.flat.casewhen;

import com.easy.query.api.proxy.util.EasyProxyParamExpressionUtil;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilder;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

/**
 * create time 2024/2/27 22:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class FlatElementCaseWhenEntityBuilder {
    private final SQLCaseWhenBuilder caseWhenBuilder;
    private final EntitySQLContext entitySQLContext;

    public FlatElementCaseWhenEntityBuilder(EntitySQLContext entitySQLContext){
        this.entitySQLContext = entitySQLContext;
        EntityExpressionBuilder entityExpressionBuilder = entitySQLContext.getEntityExpressionBuilder();
        Objects.requireNonNull(entityExpressionBuilder,"CaseWhenEntityBuilder entitySQLContext.getEntityExpressionBuilder() is null");
        this.caseWhenBuilder=entitySQLContext.getRuntimeContext().getSQLCaseWhenBuilderFactory().create(entityExpressionBuilder.getExpressionContext());
    }
    public CaseWhenThenEntityBuilder caseWhen(SQLPredicateExpression sqlPredicateExpression){
        return new CaseWhenThenEntityBuilder(this,entitySQLContext,caseWhenBuilder,sqlPredicateExpression);
    }
    public CaseWhenThenFlatJoinEntityBuilder caseWhen(SQLAggregatePredicateExpression sqlAggregatePredicateExpression){
        return new CaseWhenThenFlatJoinEntityBuilder(this,entitySQLContext,caseWhenBuilder,sqlAggregatePredicateExpression);
    }
//    public <TProperty> CaseWhenEntityBuilder<TRProxy,TR> caseWhen(SQLActionExpression sqlActionExpression, PropTypeColumn<TProperty> then){
//        caseWhenBuilder.caseWhen(filter->{
//            entitySQLContext._where(filter, sqlActionExpression);
//        },then);
//        return this;
//    }
    public <TV,TProperty> AnyTypeExpression<TProperty> elseEnd(TV elseValue){
        return EasyObjectUtil.typeCastNullable(elseEnd(elseValue,Object.class));
    }
    public <TV,TProperty> AnyTypeExpression<TProperty> elseEnd(TV elseValue, Class<TProperty> resultClass){
        ParamExpression paramExpression = EasyProxyParamExpressionUtil.getParamExpression(entitySQLContext, elseValue);
        SQLFunction sqlFunction = caseWhenBuilder.elseEnd(paramExpression);
        return new AnyTypeExpressionImpl<>(entitySQLContext,null,null, f->sqlFunction,resultClass);
    }
}