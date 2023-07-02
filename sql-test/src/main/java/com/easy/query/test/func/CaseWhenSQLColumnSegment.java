package com.easy.query.test.func;

import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/7/2 21:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenSQLColumnSegment implements SQLColumnSegment {

    private final QueryRuntimeContext runtimeContext;
    private final ExpressionContext expressionContext;
    private final TableAvailable table;
    private final List<Tuple2<SQLExpression1<SQLWherePredicate<Object>>, Object>> whens;
    private final Object elseValue;

    public CaseWhenSQLColumnSegment(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext, TableAvailable table, List<Tuple2<SQLExpression1<SQLWherePredicate<Object>>,Object>> whens, Object elseValue){
        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;
        this.table = table;

        this.whens = whens;
        this.elseValue = elseValue;
    }
    @Override
    public SQLColumnSegment cloneSQLColumnSegment() {
        return new CaseWhenSQLColumnSegment(runtimeContext,expressionContext,table,new ArrayList<>(whens),elseValue);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        StringBuilder sql = new StringBuilder();
        sql.append("CASE ");
        for (Tuple2<SQLExpression1<SQLWherePredicate<Object>>, Object> when : whens) {
            SQLExpression1<SQLWherePredicate<Object>> t1 = when.getT1();
            Object thenValue = when.getT2();
            AndPredicateSegment resolve = resolve(t1);
            String caseWhenPredicateSql = resolve.toSQL(toSQLContext);
            sql.append("WHEN ").append(caseWhenPredicateSql).append(" THEN ? ");
            toSQLContext.addParameter(new EasyConstSQLParameter(null,null,thenValue));
        }
        sql.append("ELSE ? END");
        toSQLContext.addParameter(new EasyConstSQLParameter(null,null,elseValue));
        return sql.toString();
    }
    public AndPredicateSegment resolve(SQLExpression1<SQLWherePredicate<Object>> predicate){

        AndPredicateSegment andPredicateSegment = new AndPredicateSegment(true);
        FilterImpl filter = new FilterImpl(runtimeContext,expressionContext,andPredicateSegment,false);
        WherePredicateImpl<Object> objectWherePredicate = new WherePredicateImpl<>(table, filter);
        SQLWherePredicateImpl<Object> topicSQLWherePredicate = new SQLWherePredicateImpl<>(objectWherePredicate);
        predicate.apply(topicSQLWherePredicate);
//        topicSQLWherePredicate.eq(Topic::getId,"1");
//        String sql = andPredicateSegment.toSQL(toSQLContext);
//        String s = "CASE WHEN " + sql + " THEN ? ";
        return andPredicateSegment;
    }
}
