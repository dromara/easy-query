package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.scec.expression.ColumnCollectionMultiParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnConstParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnNameExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyAsAliasParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnPropertyExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ColumnSQLParameterExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.FormatValueParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.ParamExpression;
import com.easy.query.core.expression.segment.scec.expression.SQLSegmentParamExpressionImpl;
import com.easy.query.core.expression.segment.scec.expression.SubQueryParamExpressionImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
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
    private final QueryRuntimeContext runtimeContext;
    private String alias;
    private EntityMetadata resultEntityMetadata;
    private boolean keep;

    private TableAvailable defaultTable;
    private String defaultProperty;
    private boolean nativePropertyInfo;

    public SQLNativeExpressionContextImpl(ExpressionContext expressionContext, QueryRuntimeContext runtimeContext) {

        Objects.requireNonNull(runtimeContext, "runtimeContext cannot be null");
        this.expressionContext = expressionContext;
        this.runtimeContext = runtimeContext;
        this.keep = true;
        this.nativePropertyInfo=false;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    public void expression(TableAvailable table, String property) {
        Objects.requireNonNull(table, "table cannot be null");
        Objects.requireNonNull(property, "property cannot be null");
        ColumnPropertyExpressionImpl columnPropertyExpression = new ColumnPropertyExpressionImpl(table, property,expressionContext);
        this.expressions.add(columnPropertyExpression);
        //进行原生片段支持表属性和所属表
        if(!nativePropertyInfo){
            this.nativePropertyInfo=true;
            this.defaultTable=table;
            this.defaultProperty=property;
        } else{
            this.defaultTable=null;
            this.defaultProperty=null;
        }
    }

    @Override
    public void columnName(TableAvailable table, String columnName) {
        Objects.requireNonNull(table, "table cannot be null");
        Objects.requireNonNull(columnName, "columnName cannot be null");
        ColumnNameExpressionImpl columnNameExpression = new ColumnNameExpressionImpl(table, columnName,expressionContext);
        this.expressions.add(columnNameExpression);
    }

    @Override
    public <TEntity> void expression(Query<TEntity> subQuery) {
        Objects.requireNonNull(subQuery, "subQuery cannot be null");
//        if(extract){
//            extract(subQuery);
//        }
        extract(subQuery);
        SubQueryParamExpressionImpl subQueryParamExpression = new SubQueryParamExpressionImpl(subQuery);
        this.expressions.add(subQueryParamExpression);
        this.defaultTable=null;
        this.defaultProperty=null;
    }

    public void value(Object val) {
        if (val instanceof SQLParameter) {
            ColumnSQLParameterExpressionImpl columnParamValueExpression = new ColumnSQLParameterExpressionImpl((SQLParameter) val);
            this.expressions.add(columnParamValueExpression);
        } else {
            ColumnConstParameterExpressionImpl columnConstValueExpression = new ColumnConstParameterExpressionImpl(val);
            this.expressions.add(columnConstValueExpression);
        }
    }

    @Override
    public void sql(SQLSegment sqlSegment) {
        SQLSegmentParamExpressionImpl sqlSegmentParamExpression = new SQLSegmentParamExpressionImpl(sqlSegment);
        this.expressions.add(sqlSegmentParamExpression);
    }

    @Override
    public void sqlFunction(SQLFunction sqlFunction) {
        SQLSegmentParamExpressionImpl sqlSegmentParamExpression = new SQLSegmentParamExpressionImpl(sqlFunction, expressionContext, defaultTable, runtimeContext, null);
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

    @Override
    public String getPropertyOrNull() {
        return defaultProperty;
    }

    @Override
    public TableAvailable getTableOrNull() {
        return defaultTable;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
