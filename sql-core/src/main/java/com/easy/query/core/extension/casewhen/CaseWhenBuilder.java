package com.easy.query.core.extension.casewhen;

import com.easy.query.core.common.tuple.EasyTuple2;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
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
    private List<EasyTuple2<SQLActionExpression1<Filter>, ParamExpression>> whens;

    public CaseWhenBuilder(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext){
        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;
        whens=new ArrayList<>();
    }
    public CaseWhenBuilder caseWhen(SQLActionExpression1<Filter> predicate, Object then){
        whens.add(new EasyTuple2<>(predicate,new ColumnConstParameterExpressionImpl(then)));
        return this;
    }
    public CaseWhenBuilder caseWhenColumn(SQLActionExpression1<Filter> predicate, TableAvailable table, String property){
        whens.add(new EasyTuple2<>(predicate,new ColumnPropertyExpressionImpl(table,property,expressionContext)));
        return this;
    }
    public CaseWhenBuilder caseWhenColumn(SQLActionExpression1<Filter> predicate, SQLTableOwner sqlTableOwner, String property){
        return caseWhenColumn(predicate,sqlTableOwner.getTable(),property);
    }
    public CloneableSQLSegment elseEnd(Object elseValue){
        return new CaseWhenSQLColumnSegment(expressionContext, EasyObjectUtil.typeCastNullable(whens),new ColumnConstParameterExpressionImpl(elseValue));
    }
    public CloneableSQLSegment elseEndColumn(TableAvailable table, String property){
        return new CaseWhenSQLColumnSegment(expressionContext, EasyObjectUtil.typeCastNullable(whens),new ColumnPropertyExpressionImpl(table,property,expressionContext));
    }
    public CloneableSQLSegment elseEndColumn(SQLTableOwner sqlTableOwner, String property){
        return elseEndColumn(sqlTableOwner.getTable(),property);
    }
}