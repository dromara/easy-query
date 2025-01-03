package com.easy.query.core.func.def.impl;

import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.impl.DefaultSQLSegment;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.impl.ColumSQLExpressionImpl;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2024/2/28 06:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenSQLFunction extends AbstractExpressionSQLFunction {
    private final QueryRuntimeContext runtimeContext;
    private final ExpressionContext expressionContext;
    private final List<Tuple2<SQLExpression1<Filter>, ParamExpression>> whens;
    private final ParamExpression elseValue;
    private final List<ColumnExpression> columnExpressions = new ArrayList<>();
    private final StringBuilder sql = new StringBuilder();

    public CaseWhenSQLFunction(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext, List<Tuple2<SQLExpression1<Filter>, ParamExpression>> whens, ParamExpression elseValue) {
        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;

        this.whens = whens;
        this.elseValue = elseValue;
        initCaseWhen();
    }

    private void initCaseWhen() {

        int i = 0;
        sql.append("(CASE ");
        for (Tuple2<SQLExpression1<Filter>, ParamExpression> when : whens) {
            SQLExpression1<Filter> filterExpression = when.t();
            ParamExpression paramExpression = when.t1();
            AndPredicateSegment resolve = resolve(runtimeContext, expressionContext, filterExpression);
            ColumSQLExpressionImpl columSQLExpressionPredicate = new ColumSQLExpressionImpl(new DefaultSQLSegment(toSQLContext -> resolve.toSQL(toSQLContext), visitor -> EasySQLSegmentUtil.tableVisit(resolve, visitor)));
            columnExpressions.add(columSQLExpressionPredicate);
            sql.append("WHEN ");
            sql.append("{").append(i++).append("}");
//            String caseWhenPredicateSql = resolve.toSQL(toSQLContext);

            ColumSQLExpressionImpl columSQLExpressionValue = new ColumSQLExpressionImpl(new DefaultSQLSegment(toSQLContext -> EasySQLExpressionUtil.parseParamExpression(expressionContext, paramExpression, toSQLContext).toString(), visitor -> paramExpression.accept(visitor)));
            columnExpressions.add(columSQLExpressionValue);
            sql.append(" THEN ");
            sql.append("{").append(i++).append("}").append(" ");
//            sql.append("WHEN ").append(caseWhenPredicateSql).append(" THEN ").append(thenValue).append(" ");
        }

        ColumSQLExpressionImpl columSQLExpressionElseValue = new ColumSQLExpressionImpl(new DefaultSQLSegment(toSQLContext -> EasySQLExpressionUtil.parseParamExpression(expressionContext, this.elseValue, toSQLContext).toString(), visitor -> this.elseValue.accept(visitor)));
        columnExpressions.add(columSQLExpressionElseValue);
        sql.append("ELSE ").append("{").append(i++).append("}").append(" END)");
    }

    public AndPredicateSegment resolve(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext, SQLExpression1<Filter> filterExpression) {

        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(true);
        FilterImpl filter = new FilterImpl(runtimeContext, expressionContext, andPredicateSegment, false, AnyValueFilter.DEFAULT);
        filterExpression.apply(filter);
//        topicSQLWherePredicate.eq(Topic::getId,"1");
//        String sql = andPredicateSegment.toSQL(toSQLContext);
//        String s = "CASE WHEN " + sql + " THEN ? ";
        return andPredicateSegment;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return sql.toString();
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
