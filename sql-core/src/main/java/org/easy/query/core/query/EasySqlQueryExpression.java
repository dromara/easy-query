package org.easy.query.core.query;

import org.easy.query.core.abstraction.EasyQueryLambdaFactory;
import org.easy.query.core.abstraction.EasyQueryRuntimeContext;
import org.easy.query.core.abstraction.EasySqlExpressionFactory;
import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.basic.jdbc.parameter.SQLParameter;
import org.easy.query.core.configuration.types.EasyQueryConfiguration;
import org.easy.query.core.configuration.types.GlobalQueryFilterConfiguration;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.segment.builder.GroupBySqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.OrderBySqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.util.ArrayUtil;
import org.easy.query.core.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @FileName: EasySqlExpressionSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 22:10
 * @Created by xuejiaming
 */
public abstract class EasySqlQueryExpression extends AbstractSqlEntityExpression implements SqlEntityQueryExpression {

    protected PredicateSegment where;
    protected SqlBuilderSegment group;
    protected PredicateSegment having;
    protected SqlBuilderSegment order;
    protected long offset;
    protected long rows;

    protected final SqlBuilderSegment projects;

    public EasySqlQueryExpression(SqlExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
        this.projects = new ProjectSqlBuilderSegment();
    }
    @Override
    public boolean isEmpty() {
        return tables.isEmpty();
    }

    @Override
    public SqlBuilderSegment getProjects() {
        return projects;
    }

    @Override
    public PredicateSegment getWhere() {
        if (where == null) {
            where = new AndPredicateSegment(true);
        }
        return where;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public void setOffset(long offset) {
        this.offset = offset;
    }

    @Override
    public long getRows() {
        return rows;
    }

    @Override
    public void setRows(long rows) {
        this.rows = rows;
    }

    @Override
    public boolean hasLimit() {
        return this.rows>0;
    }

    @Override
    public boolean hasWhere() {
        return where != null && where.isNotEmpty();
    }

    @Override
    public PredicateSegment getHaving() {
        if (having == null) {
            having = new AndPredicateSegment(true);
        }
        return having;
    }

    @Override
    public boolean hasHaving() {
        return having != null && having.isNotEmpty();
    }

    @Override
    public SqlBuilderSegment getGroup() {
        if (group == null) {
            group = new GroupBySqlBuilderSegment();
        }
        return group;
    }

    @Override
    public boolean hasGroup() {
        return group != null && group.isNotEmpty();
    }

    @Override
    public SqlBuilderSegment getOrder() {
        if (order == null) {
            order = new OrderBySqlBuilderSegment();
        }
        return order;
    }

    @Override
    public boolean hasOrder() {
        return order != null && order.isNotEmpty();
    }





    @Override
    public String toSql() {
        sqlExpressionContext.clearParameters();
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        String select = getProjects().toSql();
        Iterator<SqlEntityTableExpression> iterator = getTables().iterator();
        SqlEntityTableExpression firstTable = iterator.next();
        if (tableCount == 1 && firstTable instanceof AnonymousEntityTableExpression && StringUtil.isEmpty(select)) {
            return toTableExpressionSql(firstTable,true);
        }
        StringBuilder sql = new StringBuilder("SELECT ");

        if (StringUtil.isEmpty(select)) {
            if (!hasGroup()) {
                sql.append(firstTable.getAlias()).append(".*");
            } else {
                sql.append(getGroup().toSql());
            }
        } else {
            sql.append(select);
        }
        sql.append(toTableExpressionSql(firstTable,false));
        while (iterator.hasNext()) {
            SqlEntityTableExpression table = iterator.next();
            sql.append(toTableExpressionSql(table,false));// [from table alias] | [left join table alias] 匿名表 应该使用  [left join (table) alias]

            PredicateSegment on = getTableOnWithQueryFilter(table);
            if (on != null && on.isNotEmpty()) {
                sql.append(" ON ").append(on.toSql());
            }
        }
        PredicateSegment where = getSqlWhereWithQueryFilter();
        if (where != null && where.isNotEmpty()) {
            sql.append(" WHERE ").append(where.toSql());
        }
        if (hasGroup()) {
            sql.append(" GROUP BY ").append(getGroup().toSql());
        }
        if (hasHaving()) {
            sql.append(" HAVING ").append(getHaving().toSql());
        }
        if (hasOrder()) {
            sql.append(" ORDER BY ").append(getOrder().toSql());
        }
        if (getRows() > 0) {
            sql.append(" LIMIT ");
            if (getOffset() > 0) {
                sql.append(getOffset()).append(" OFFSET ").append(getRows());
            } else {
                sql.append(getRows());
            }
        }
        return sql.toString();
    }

    private String toTableExpressionSql(SqlEntityTableExpression sqlEntityTableExpression,boolean onlySingleAnonymousTable) {
        if (sqlEntityTableExpression instanceof AnonymousEntityTableExpression) {

            SqlEntityQueryExpression sqlEntityQueryExpression = ((AnonymousEntityTableExpression) sqlEntityTableExpression).getSqlEntityQueryExpression();
            //如果只有单匿名表且未对齐select那么嵌套表需要被展开
            //todo 如果对其进行order 或者 where了呢怎么办
            String s =onlySingleAnonymousTable?sqlEntityQueryExpression.toSql():sqlEntityTableExpression.toSql();
            sqlExpressionContext.extractParameters(sqlEntityQueryExpression.getSqlExpressionContext());
            return s;
        }
        return sqlEntityTableExpression.toSql();
    }

    private PredicateSegment getTableOnWithQueryFilter(SqlEntityTableExpression table) {
        return getSqlPredicateSegment(table, table.hasOn() ? table.getOn() : null);
    }

    private PredicateSegment getSqlWhereWithQueryFilter() {
        SqlEntityTableExpression table = getTable(0);
        return getSqlPredicateSegment(table, hasWhere() ? getWhere() : null);
    }

    private PredicateSegment getSqlPredicateSegment(SqlEntityTableExpression table, PredicateSegment originalPredicate) {

        EntityMetadata entityMetadata = table.getEntityMetadata();
        boolean useLogicDelete = entityMetadata.enableLogicDelete() && sqlExpressionContext.isUseLogicDelete();
        boolean useQueryFilter = entityMetadata.hasAnyQueryFilter() && sqlExpressionContext.isUserQueryFilter();
        if(useLogicDelete||useQueryFilter){
            PredicateSegment predicateSegment = new AndPredicateSegment(true);
            EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
            SqlPredicate<?> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), this, predicateSegment);
            SqlExpression<SqlPredicate<?>> logicDeleteQueryFilterExpression = table.getLogicDeleteQueryFilterExpression();
            if (logicDeleteQueryFilterExpression != null) {
                logicDeleteQueryFilterExpression.apply(sqlPredicate);
            }
            List<String> queryFilterNames = table.getQueryFilterNames();
            if(ArrayUtil.isNotEmpty(queryFilterNames)){
                EasyQueryConfiguration easyQueryConfiguration = getRuntimeContext().getEasyQueryConfiguration();
                for (String queryFilterName : queryFilterNames) {
                    GlobalQueryFilterConfiguration globalQueryFilterConfiguration = easyQueryConfiguration.getGlobalQueryFilterConfiguration(queryFilterName);
                    if(globalQueryFilterConfiguration!=null){
                        globalQueryFilterConfiguration.configure(table.entityClass(),sqlPredicate);
                    }
                }
            }

            if(predicateSegment.isNotEmpty()){
                if (originalPredicate != null && originalPredicate.isNotEmpty()) {
                    predicateSegment.addPredicateSegment(originalPredicate);
                }
                return predicateSegment;
            }
        }
        return originalPredicate;
    }

}
