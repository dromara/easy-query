package com.easy.query.core.extension.casewhen;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.ConditionAllAccepter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/7/2 21:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenSQLColumnSegment implements CloneableSQLSegment {

    private final QueryRuntimeContext runtimeContext;
    private final ExpressionContext expressionContext;
    private final List<Tuple2<SQLExpression1<Filter>, ParamExpression>> whens;
    private final ParamExpression elseValue;

    public CaseWhenSQLColumnSegment(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext, List<Tuple2<SQLExpression1<Filter>,ParamExpression>> whens, ParamExpression elseValue){
        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;

        this.whens = whens;
        this.elseValue = elseValue;
    }
    @Override
    public CloneableSQLSegment cloneSQLColumnSegment() {
        return new CaseWhenSQLColumnSegment(runtimeContext,expressionContext,new ArrayList<>(whens),elseValue);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        StringBuilder sql = new StringBuilder();
        sql.append("CASE ");
        for (Tuple2<SQLExpression1<Filter>, ParamExpression> when : whens) {
            SQLExpression1<Filter> filterExpression = when.t();
            ParamExpression paramExpression = when.t1();
            AndPredicateSegment resolve = resolve(filterExpression);
            String caseWhenPredicateSql = resolve.toSQL(toSQLContext);
            Object thenValue = EasySQLExpressionUtil.parseParamExpression(runtimeContext, paramExpression, toSQLContext);
            sql.append("WHEN ").append(caseWhenPredicateSql).append(" THEN ").append(thenValue).append(" ");
        }
        Object elseValue = EasySQLExpressionUtil.parseParamExpression(runtimeContext, this.elseValue, toSQLContext);
        sql.append("ELSE ").append(elseValue).append(" END");
        return sql.toString();
    }
    public AndPredicateSegment resolve(SQLExpression1<Filter> filterExpression){

        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(true);
        FilterImpl filter = new FilterImpl(runtimeContext,expressionContext,andPredicateSegment,false, ConditionAllAccepter.DEFAULT);
        filterExpression.apply(filter);
//        topicSQLWherePredicate.eq(Topic::getId,"1");
//        String sql = andPredicateSegment.toSQL(toSQLContext);
//        String s = "CASE WHEN " + sql + " THEN ? ";
        return andPredicateSegment;
    }

}
