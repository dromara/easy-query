package org.easy.query.mysql.base;

import org.easy.query.core.abstraction.EasyQueryLambdaFactory;
import org.easy.query.core.abstraction.EasySqlExpressionFactory;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.segment.SqlSegment;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.query.*;
import org.easy.query.core.util.StringUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @FileName: MySQLEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 13:04
 * @Created by xuejiaming
 */
public class MySQLQueryExpression extends EasySqlQueryExpression {
    public MySQLQueryExpression(SqlExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
    }

    @Override
    public String toSql() {
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        String select = getProjects().toSql();
        Iterator<SqlEntityTableExpression> iterator = getTables().iterator();
        SqlEntityTableExpression firstTable = iterator.next();
        if(tableCount==1&&firstTable instanceof  AnonymousEntityTableExpression&&StringUtil.isEmpty(select)){
            return ((AnonymousEntityTableExpression)firstTable).getSqlEntityQueryExpression().toSql();
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
        sql.append(firstTable.toSql());
        while(iterator.hasNext()){
            SqlEntityTableExpression table = iterator.next();
            sql.append(table.toSql());// [from table alias] | [left join table alias] 匿名表 应该使用  [left join (table) alias]

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

    private PredicateSegment getTableOnWithQueryFilter(SqlEntityTableExpression table) {
        return getSqlPredicateSegment(table, table.hasOn() ? table.getOn() : null);
    }

    private PredicateSegment getSqlWhereWithQueryFilter() {
        SqlEntityTableExpression table = getTable(0);
        return getSqlPredicateSegment(table, hasWhere() ? getWhere() : null);
    }

    private PredicateSegment getSqlPredicateSegment(SqlEntityTableExpression table, PredicateSegment originalPredicate) {
        PredicateSegment predicateSegment = null;
        SqlExpression<SqlPredicate<?>> queryFilterExpression = table.getQueryFilterExpression();
        if (queryFilterExpression != null) {
            predicateSegment = new AndPredicateSegment(true);
            EasyQueryLambdaFactory easyQueryLambdaFactory = getRuntimeContext().getEasyQueryLambdaFactory();
            SqlPredicate<?> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), this, predicateSegment);
            queryFilterExpression.apply(sqlPredicate);
            if (originalPredicate != null && originalPredicate.isNotEmpty()) {
                predicateSegment.addPredicateSegment(originalPredicate);
            }
        } else {
            predicateSegment = originalPredicate;
        }
        return predicateSegment;
    }

    @Override
    public SqlEntityQueryExpression cloneSqlQueryExpression() {

        SqlExpressionContext sqlExpressionContext = getSqlExpressionContext().cloneSqlExpressionContext();
        EasySqlExpressionFactory sqlExpressionFactory = getRuntimeContext().getSqlExpressionFactory();
        MySQLQueryExpression sqlEntityQueryExpression = (MySQLQueryExpression)sqlExpressionFactory.createSqlEntityQueryExpression(sqlExpressionContext);
        sqlEntityQueryExpression.where=super.where;
        sqlEntityQueryExpression.group=super.group;
        sqlEntityQueryExpression.having=super.having;
        sqlEntityQueryExpression.order=super.order;
        sqlEntityQueryExpression.offset=super.offset;
        sqlEntityQueryExpression.rows=super.rows;
        List<SqlSegment> sqlSegments = super.projects.getSqlSegments();
        sqlEntityQueryExpression.projects.getSqlSegments().addAll(sqlSegments);
        sqlEntityQueryExpression.tables.addAll(super.tables);
        return sqlEntityQueryExpression;
    }
}
