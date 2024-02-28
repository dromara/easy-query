package com.easy.query.core.extension.casewhen;

import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstSQLParameterExpressionImpl;
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
    private List<Tuple2<SQLExpression1<Filter>, ParamExpression>> whens;

    public CaseWhenBuilderExpression(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext){
        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;
        whens=new ArrayList<>();
    }
    public CaseWhenBuilderExpression caseWhen(SQLExpression1<Filter> predicate, Object then){
        whens.add(new Tuple2<>(predicate,new ColumnConstSQLParameterExpressionImpl(then)));
        return this;
    }
    public CaseWhenBuilderExpression caseWhenColumn(SQLExpression1<Filter> predicate, TableAvailable table, String property){
        whens.add(new Tuple2<>(predicate,new ColumnPropertyExpressionImpl(table,property)));
        return this;
    }
    public CaseWhenBuilderExpression caseWhenColumn(SQLExpression1<Filter> predicate, SQLTableOwner sqlTableOwner, String property){
        return caseWhenColumn(predicate,sqlTableOwner.getTable(),property);
    }
    public SQLFunction elseEnd(Object elseValue){
        return new CaseWhenSQLFunction(whens,new ColumnConstSQLParameterExpressionImpl(elseValue));
    }
    public SQLFunction elseEndColumn(TableAvailable table, String property){
        return new CaseWhenSQLFunction(whens,new ColumnPropertyExpressionImpl(table,property));
    }
    public SQLFunction elseEndColumn(SQLTableOwner sqlTableOwner, String property){
        return elseEndColumn(sqlTableOwner.getTable(),property);
    }
}