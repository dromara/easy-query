package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstSQLParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnSQLParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.FormatValueParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.segment.scec.expression.SubQueryParamExpressionImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/7/29 17:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeExpressionContextImpl implements SQLNativeExpressionContext {
    protected final List<ParamExpression> expressions=new ArrayList<>();
    protected final ExpressionContext expressionContext;
    private String alias;

    public SQLNativeExpressionContextImpl(ExpressionContext expressionContext){

        this.expressionContext = expressionContext;
    }
    public SQLNativeExpressionContextImpl expression(TableAvailable table, String property){
        Objects.requireNonNull(table, "table cannot be null");
        Objects.requireNonNull(property, "property cannot be null");
        ColumnPropertyExpressionImpl columnPropertyExpression = new ColumnPropertyExpressionImpl(table, property);
        expressions.add(columnPropertyExpression);
        return this;
    }

    @Override
    public <TEntity> SQLNativeExpressionContext expression(Query<TEntity> subQuery) {
        Objects.requireNonNull(subQuery, "subQuery cannot be null");
        extract(subQuery);
        SubQueryParamExpressionImpl subQueryParamExpression = new SubQueryParamExpressionImpl(subQuery);
        expressions.add(subQueryParamExpression);
        return this;
    }

    public SQLNativeExpressionContextImpl value(Object val){
        if(val instanceof SQLParameter){
            ColumnSQLParameterExpressionImpl columnParamValueExpression = new ColumnSQLParameterExpressionImpl((SQLParameter) val);
            expressions.add(columnParamValueExpression);
        }else{
            ColumnConstSQLParameterExpressionImpl columnConstValueExpression = new ColumnConstSQLParameterExpressionImpl(val);
            expressions.add(columnConstValueExpression);
        }
        return this;
    }

    @Override
    public SQLNativeExpressionContext format(Object formatVal) {
        FormatValueParamExpressionImpl constValueParamExpression = new FormatValueParamExpressionImpl(formatVal);
        expressions.add(constValueParamExpression);
        return this;
    }

    public List<ParamExpression> getExpressions() {
        return expressions;
    }

    public String getAlias() {
        return alias;
    }

    public SQLNativeExpressionContext setAlias(String alias) {
        this.alias = alias;
        return this;
    }
    private <T2> void extract(Query<T2> subQuery) {
        Objects.requireNonNull(expressionContext, "expressionContext cannot be null");
        EntityQueryExpressionBuilder subQueryableSQLEntityExpressionBuilder = subQuery.getSQLEntityExpressionBuilder();
        expressionContext.extract(subQueryableSQLEntityExpressionBuilder.getExpressionContext());
    }
}
