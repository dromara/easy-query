package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.SubQueryColumnSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.parse.SubQueryColumnParseResult;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

/**
 * create time 2023/5/20 10:08
 * 子查询片段实现
 *
 * @author xuejiaming
 */
public class SubQueryColumnSegmentImpl implements SubQueryColumnSegment {
    private final TableAvailable table;
    private final Query<?> subQuery;
    private String alias;
    private final QueryRuntimeContext runtimeContext;
    private final boolean isAggregateColumn;
    private final AggregationType aggregationType;

    public SubQueryColumnSegmentImpl(TableAvailable table, Query<?> subQuery, String alias, QueryRuntimeContext runtimeContext) {

        this.table = table;
        this.subQuery = subQuery;
        this.alias = alias;
        this.runtimeContext = runtimeContext;
        SubQueryColumnParseResult subQueryColumnParseResult = parseSubQueryAggregate(subQuery);
        this.isAggregateColumn = subQueryColumnParseResult.isAggregateColumn();
        this.aggregationType = subQueryColumnParseResult.getAggregationType();
    }

    private SubQueryColumnParseResult parseSubQueryAggregate(Query<?> subQuery) {
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = subQuery.getSQLEntityExpressionBuilder();
        return parseEasyQueryExpressionBuilder(sqlEntityExpressionBuilder);
    }

    private SubQueryColumnParseResult parseEasyQueryExpressionBuilder(EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        SQLBuilderSegment projects = entityQueryExpressionBuilder.getProjects();
        if (EasySQLSegmentUtil.isNotEmpty(projects)) {
            if (projects instanceof ProjectSQLBuilderSegment) {
                ProjectSQLBuilderSegment projectSQLBuilderSegment = (ProjectSQLBuilderSegment) projects;
                SQLSegment sqlSegment = projectSQLBuilderSegment.getSQLSegments().get(0);
                if (sqlSegment instanceof FuncColumnSegment) {
                    FuncColumnSegment aggregationColumnSegment = (FuncColumnSegment) sqlSegment;
                    return new SubQueryColumnParseResult(true,aggregationColumnSegment.getAggregationType());
                }
            }
        }else if(EasyCollectionUtil.isSingle(entityQueryExpressionBuilder.getTables())){
            EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(0);
            if(table instanceof AnonymousEntityTableExpressionBuilder){
                AnonymousEntityTableExpressionBuilder anonymousEntityTableExpressionBuilder = (AnonymousEntityTableExpressionBuilder) table;
                return parseEasyQueryExpressionBuilder(anonymousEntityTableExpressionBuilder.getEntityQueryExpressionBuilder());
            }
        }
        return SubQueryColumnParseResult.DEFAULT;
    }
    @Override
    public String getAlias() {
        return alias;
    }
    @Override
    public void setAlias(String alias) {
        this.alias=alias;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String queryableSQL = subQuery.toSQL(toSQLContext);
        StringBuilder sql = new StringBuilder();
        sql.append("(");
        sql.append(queryableSQL);
        sql.append(")");
        if(alias!=null){
            sql.append(" AS ").append(EasySQLExpressionUtil.getQuoteName(runtimeContext,alias));
        }
        return sql.toString();
    }

    @Override
    public Query<?> getSubQuery() {
        return subQuery;
    }

    @Override
    public boolean isAggregateColumn() {
        return isAggregateColumn;
    }

    @Override
    public AggregationType getAggregationType() {
        return aggregationType;
    }

    @Override
    public SubQueryColumnSegment cloneSQLColumnSegment() {
        return new SubQueryColumnSegmentImpl(table, subQuery.cloneQueryable(), alias, runtimeContext);
    }

    @Override
    public void accept(TableVisitor visitor) {
        SubQueryColumnSegment.super.accept(visitor);
        subQuery.getSQLEntityExpressionBuilder().accept(visitor);
    }
}
