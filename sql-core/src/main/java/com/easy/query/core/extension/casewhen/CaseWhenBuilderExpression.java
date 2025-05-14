package com.easy.query.core.extension.casewhen;

import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.impl.CaseWhenSQLFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/7/3 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenBuilderExpression {
    private final QueryRuntimeContext runtimeContext;
    private final ExpressionContext expressionContext;
    private final List<Tuple2<SQLActionExpression1<Filter>, ParamExpression>> whens;

    public CaseWhenBuilderExpression(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext){
        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;
        whens=new ArrayList<>();
    }
    public CaseWhenBuilderExpression caseWhen(SQLActionExpression1<Filter> predicate, Object then){
        return caseWhen(predicate,new ColumnConstParameterExpressionImpl(then));
    }
    public CaseWhenBuilderExpression caseWhen(SQLActionExpression1<Filter> predicate, ParamExpression paramExpression){
        whens.add(new Tuple2<>(predicate,paramExpression));
        return this;
    }
    public SQLFunction elseEnd(Object elseValue){
        return elseEnd(new ColumnConstParameterExpressionImpl(elseValue));
    }
    public SQLFunction elseEnd(ParamExpression paramExpression){
        return new CaseWhenSQLFunction(runtimeContext,expressionContext,whens,paramExpression);
    }
    public SQLFunction elseEndColumn(TableAvailable table, String property){
        return new CaseWhenSQLFunction(runtimeContext,expressionContext,whens,new ColumnPropertyExpressionImpl(table,property,expressionContext));
    }
    public SQLFunction elseEndColumn(SQLTableOwner sqlTableOwner, String property){
        return elseEndColumn(sqlTableOwner.getTable(),property);
    }
}