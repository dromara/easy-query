package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.scec.expression.ColumnCollectionMultiParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstSQLParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyAsAliasParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnSQLParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.FormatValueParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.segment.scec.expression.SQLSegmentParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.SubQueryParamExpressionImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/7/29 17:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeExpressionContextImpl implements SQLNativeExpressionContext {
    protected final List<ParamExpression> expressions = new ArrayList<>();
    protected final ExpressionContext expressionContext;
    private String alias;
    private EntityMetadata resultEntityMetadata;
    private boolean keep;

    public SQLNativeExpressionContextImpl(ExpressionContext expressionContext, QueryRuntimeContext runtimeContext) {

        Objects.requireNonNull(runtimeContext, "runtimeContext cannot be null");
        this.expressionContext = expressionContext;
        this.keep = runtimeContext.getQueryConfiguration().getEasyQueryOption().isKeepNativeStyle();
    }

    public void expression(TableAvailable table, String property) {
        Objects.requireNonNull(table, "table cannot be null");
        Objects.requireNonNull(property, "property cannot be null");
        ColumnPropertyExpressionImpl columnPropertyExpression = new ColumnPropertyExpressionImpl(table, property);
        this.expressions.add(columnPropertyExpression);
    }

    @Override
    public <TEntity> void expression(Query<TEntity> subQuery) {
        Objects.requireNonNull(subQuery, "subQuery cannot be null");
        extract(subQuery);
        SubQueryParamExpressionImpl subQueryParamExpression = new SubQueryParamExpressionImpl(subQuery);
        this.expressions.add(subQueryParamExpression);
    }

    public void value(Object val) {
        if (val instanceof SQLParameter) {
            ColumnSQLParameterExpressionImpl columnParamValueExpression = new ColumnSQLParameterExpressionImpl((SQLParameter) val);
            this.expressions.add(columnParamValueExpression);
        } else {
            ColumnConstSQLParameterExpressionImpl columnConstValueExpression = new ColumnConstSQLParameterExpressionImpl(val);
            this.expressions.add(columnConstValueExpression);
        }
    }

    @Override
    public void sql(SQLSegment sqlSegment) {
        SQLSegmentParamExpressionImpl sqlSegmentParamExpression = new SQLSegmentParamExpressionImpl(sqlSegment);
        this.expressions.add(sqlSegmentParamExpression);
    }

    @Override
    public <T> void collection(Collection<T> values) {
        ColumnCollectionMultiParamExpressionImpl columnCollectionMultiParamExpression = new ColumnCollectionMultiParamExpressionImpl(values);
        this.expressions.add(columnCollectionMultiParamExpression);
    }

    @Override
    public void format(Object formatVal) {
        FormatValueParamExpressionImpl constValueParamExpression = new FormatValueParamExpressionImpl(formatVal);
        this.expressions.add(constValueParamExpression);
    }

    public List<ParamExpression> getExpressions() {
        return this.expressions;
    }

    public String getAlias() {
        return this.alias;
    }

    @Override
    public boolean isKeepStyle() {
        return this.keep;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public void keepStyle() {
        keep = true;
    }

    @Override
    public void messageFormat() {
        keep = false;
    }

    @Override
    public void expressionAlias(String property) {
        Objects.requireNonNull(this.resultEntityMetadata, "result entity metadata cannot be null, plz use in select as sql context");
        Objects.requireNonNull(property, "property cannot be null");
        ColumnPropertyAsAliasParamExpressionImpl columnPropertyAsAliasParamExpression = new ColumnPropertyAsAliasParamExpressionImpl(resultEntityMetadata.getColumnName(property));
        this.expressions.add(columnPropertyAsAliasParamExpression);
    }

    @Override
    public void setPropertyAlias(String property) {
        Objects.requireNonNull(this.resultEntityMetadata, "result entity metadata cannot be null, plz use in select as sql context");
        Objects.requireNonNull(property, "property cannot be null");
        this.setAlias(this.resultEntityMetadata.getColumnName(property));
    }

    @Override
    public void setResultEntityMetadata(EntityMetadata entityMetadata) {
        if(this.resultEntityMetadata!=null){
            throw new NullPointerException("result entity metadata can not repeat set");
        }
        this.resultEntityMetadata=entityMetadata;
    }

    private <T2> void extract(Query<T2> subQuery) {
        Objects.requireNonNull(this.expressionContext, "expressionContext cannot be null");
        EntityQueryExpressionBuilder subQueryableSQLEntityExpressionBuilder = subQuery.getSQLEntityExpressionBuilder();
        this.expressionContext.extract(subQueryableSQLEntityExpressionBuilder.getExpressionContext());
    }
}
