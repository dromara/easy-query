package com.easy.query.core.extension.casewhen;

import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/7/3 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenBuilder {
    private final QueryRuntimeContext runtimeContext;
    private final ExpressionContext expressionContext;
    private List<Tuple2<SQLExpression1<Filter>,Object>> whens;

    public CaseWhenBuilder(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext){
        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;
        whens=new ArrayList<>();
    }
    public CaseWhenBuilder caseWhen(SQLExpression1<Filter> predicate, Object then){
        whens.add(new Tuple2<>(predicate,then));
        return this;
    }
    public SQLColumnSegment elseEnd(Object elseValue){
        return new CaseWhenSQLColumnSegment(runtimeContext,expressionContext, EasyObjectUtil.typeCastNullable(whens),elseValue);
    }
}