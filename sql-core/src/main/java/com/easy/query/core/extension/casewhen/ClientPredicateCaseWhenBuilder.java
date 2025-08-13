package com.easy.query.core.extension.casewhen;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.GroupJoinColumnSegmentImpl;
import com.easy.query.core.expression.segment.GroupJoinPredicateSegmentContext;
import com.easy.query.core.expression.segment.impl.DefaultSQLSegment;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.util.EasyParamExpressionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;


/**
 * create time 2025/7/26 19:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClientPredicateCaseWhenBuilder {
    private final QueryRuntimeContext runtimeContext;
    private final ExpressionContext expressionContext;
    private final GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext;
    private  Object then;

    public ClientPredicateCaseWhenBuilder(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext, GroupJoinPredicateSegmentContext groupJoinPredicateSegmentContext){
        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;
        this.groupJoinPredicateSegmentContext = groupJoinPredicateSegmentContext;
    }

    public <TV> ClientPredicateCaseWhenBuilder then(TV then) {
        this.then=then;

        return this;
    }
    public <TV> SQLFunction elseEnd(TV elseValue) {
        ParamExpression thenParamExpression = EasyParamExpressionUtil.getParamExpression(then);
        ParamExpression elseEndParamExpression = EasyParamExpressionUtil.getParamExpression(elseValue);

        DefaultSQLSegment thenSegment = new DefaultSQLSegment(toSQLContext -> EasySQLExpressionUtil.parseParamExpression(expressionContext, thenParamExpression, toSQLContext).toString(), visitor -> thenParamExpression.accept(visitor));
        DefaultSQLSegment elseSegment = new DefaultSQLSegment(toSQLContext -> EasySQLExpressionUtil.parseParamExpression(expressionContext, elseEndParamExpression, toSQLContext).toString(), visitor -> elseEndParamExpression.accept(visitor));
        GroupJoinColumnSegmentImpl groupJoinColumnSegment = new GroupJoinColumnSegmentImpl(groupJoinPredicateSegmentContext, thenSegment, elseSegment);
        return runtimeContext.fx().anySQLFunction("{0}",c->{
            c.sql(groupJoinColumnSegment);
        });
    }
}
