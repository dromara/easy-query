package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.func.AggregationType;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

/**
 * create time 2023/5/20 10:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class SubQueryColumnSegmentImpl implements SubQueryColumnSegment{
    private final TableAvailable table;
    private final Queryable<?> subQueryable;
    private final String alias;
    private final QueryRuntimeContext runtimeContext;
    private final boolean isAggregateColumn;
    private final AggregationType aggregationType;

    public SubQueryColumnSegmentImpl(TableAvailable table, Queryable<?> subQueryable, String alias, QueryRuntimeContext runtimeContext){

        this.table = table;
        this.subQueryable = subQueryable;
        this.alias = alias;
        this.runtimeContext = runtimeContext;
        SubQueryColumnParseResult subQueryColumnParseResult = parseSubQueryAggregate(subQueryable);
        this.isAggregateColumn=subQueryColumnParseResult.isAggregateColumn();
        this.aggregationType=subQueryColumnParseResult.getAggregationType();
    }

    private SubQueryColumnParseResult parseSubQueryAggregate(Queryable<?> subQueryable){
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = subQueryable.getSQLEntityExpressionBuilder();
        return parseEasyQueryExpressionBuilder(sqlEntityExpressionBuilder);
    }
    private SubQueryColumnParseResult parseEasyQueryExpressionBuilder(EntityQueryExpressionBuilder entityQueryExpressionBuilder){
        SQLBuilderSegment projects = entityQueryExpressionBuilder.getProjects();
        if(EasySQLSegmentUtil.isNotEmpty(projects)){
            if(projects instanceof ProjectSQLBuilderSegment){
                ProjectSQLBuilderSegment projectSQLBuilderSegment = (ProjectSQLBuilderSegment) projects;
                SQLSegment sqlSegment = projectSQLBuilderSegment.getSQLSegments().get(0);
                if(sqlSegment instanceof  AggregationColumnSegment){
                    AggregationColumnSegment aggregationColumnSegment = (AggregationColumnSegment) sqlSegment;
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
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String queryableSQL = subQueryable.toSQL(toSQLContext);
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
    public Queryable<?> getSubQueryable() {
        return subQueryable;
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
    public SubQueryColumnSegment cloneSQLEntitySegment() {
        return new SubQueryColumnSegmentImpl(table,subQueryable.cloneQueryable(),alias,runtimeContext);
    }
}
