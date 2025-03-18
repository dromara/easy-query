package com.easy.query.core.extension.casewhen;

import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.impl.CaseWhenSQLFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2025/3/18 19:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCaseWhenBuilder implements SQLCaseWhenBuilder {
    protected final QueryRuntimeContext runtimeContext;
    protected final ExpressionContext expressionContext;
    protected final List<Tuple2<SQLExpression1<Filter>, ParamExpression>> whens;

    public DefaultCaseWhenBuilder(ExpressionContext expressionContext) {
        this.runtimeContext = expressionContext.getRuntimeContext();
        this.expressionContext = expressionContext;
        whens = new ArrayList<>();
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    public SQLCaseWhenBuilder caseWhen(SQLExpression1<Filter> predicate, ParamExpression paramExpression) {
        whens.add(new Tuple2<>(predicate, paramExpression));
        return this;
    }

    public SQLFunction elseEnd(ParamExpression paramExpression) {
        return new CaseWhenSQLFunction(runtimeContext, expressionContext, whens, paramExpression);
    }
}
