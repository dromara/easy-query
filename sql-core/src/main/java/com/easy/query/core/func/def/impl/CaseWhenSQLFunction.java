package com.easy.query.core.func.def.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLLazyFunction;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.util.List;

/**
 * create time 2024/2/28 06:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenSQLFunction implements SQLLazyFunction {
    private final List<Tuple2<SQLExpression1<Filter>, ParamExpression>> whens;
    private final ParamExpression elseValue;

    public CaseWhenSQLFunction(List<Tuple2<SQLExpression1<Filter>, ParamExpression>> whens, ParamExpression elseValue){

        this.whens = whens;
        this.elseValue = elseValue;
    }
    @Override
    public String toSQL(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext, ToSQLContext toSQLContext) {

        StringBuilder sql = new StringBuilder();
        sql.append("(CASE ");
        for (Tuple2<SQLExpression1<Filter>, ParamExpression> when : whens) {
            SQLExpression1<Filter> filterExpression = when.t();
            ParamExpression paramExpression = when.t1();
            AndPredicateSegment resolve = resolve(runtimeContext,expressionContext,filterExpression);
            String caseWhenPredicateSql = resolve.toSQL(toSQLContext);
            Object thenValue = EasySQLExpressionUtil.parseParamExpression(runtimeContext, paramExpression, toSQLContext);
            sql.append("WHEN ").append(caseWhenPredicateSql).append(" THEN ").append(thenValue).append(" ");
        }
        Object elseValue = EasySQLExpressionUtil.parseParamExpression(runtimeContext, this.elseValue, toSQLContext);
        sql.append("ELSE ").append(elseValue).append(" END)");
        return sql.toString();
    }
    public AndPredicateSegment resolve(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext,SQLExpression1<Filter> filterExpression){

        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(true);
        FilterImpl filter = new FilterImpl(runtimeContext,expressionContext,andPredicateSegment,false, AnyValueFilter.DEFAULT);
        filterExpression.apply(filter);
//        topicSQLWherePredicate.eq(Topic::getId,"1");
//        String sql = andPredicateSegment.toSQL(toSQLContext);
//        String s = "CASE WHEN " + sql + " THEN ? ";
        return andPredicateSegment;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return null;
    }

    @Override
    public int paramMarks() {
        return 0;
    }

    @Override
    public void consume(SQLNativeChainExpressionContext context) {

    }

    @Override
    public AggregationType getAggregationType() {
        return AggregationType.UNKNOWN;
    }
}
