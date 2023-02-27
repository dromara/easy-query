package org.easy.query.mysql.util;

import org.easy.query.core.abstraction.EasyQueryLambdaFactory;
import org.easy.query.core.basic.sql.segment.segment.AndPredicateSegment;
import org.easy.query.core.basic.sql.segment.segment.PredicateSegment;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.impl.InsertContext;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.impl.UpdateContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.util.StringUtil;

/**
 * @FileName: MySQLUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/10 08:28
 * @Created by xuejiaming
 */
public class MySQLUtil {
    private MySQLUtil() {
        throw new UnsupportedOperationException();
    }


    /**
     * 生成mysql语句
     *
     * @param selectContext
     * @param select
     * @return
     */
    public static String toSelectSql(SelectContext selectContext, String select) {

        //将条件参数清空
        if (!selectContext.getParameters().isEmpty()) {
            selectContext.getParameters().clear();
        }
        int tableCount = selectContext.getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        StringBuilder sql = new StringBuilder("SELECT ");
        for (int i = 0; i < tableCount; i++) {
            SqlTableInfo table = selectContext.getTable(i);
            if (i == 0) {
                if (StringUtil.isEmpty(select)) {
                    if (!selectContext.hasGroup()) {
                        sql.append(table.getAlias()).append(".*");
                    } else {
                        sql.append(selectContext.getGroup().toSql());
                    }
                } else {
                    sql.append(select);
                }
            }

            sql.append(table.getSelectTableSource()).append(table.getEntityMetadata().getTableName()).append(" ").append(table.getAlias());

            if (i > 0) {
                PredicateSegment on = getTableOnWithQueryFilter(selectContext, table);
                if (on != null&&on.isNotEmpty()) {
                    sql.append(" ON ").append(on.getSql());
                }
            }
        }
        PredicateSegment where = getSqlWhereWithQueryFilter(selectContext);
        if (where != null&&where.isNotEmpty()) {
            sql.append(" WHERE ").append(where.getSql());
        }
        if (selectContext.hasGroup()) {
            sql.append(" GROUP BY ").append(selectContext.getGroup().toSql());
        }
        if (selectContext.hasHaving()) {
            sql.append(" HAVING ").append(selectContext.getHaving().getSql());
        }
        if (selectContext.hasOrder()) {
            sql.append(" ORDER BY ").append(selectContext.getOrder().toSql());
        }
        if (selectContext.getRows() > 0) {
            sql.append(" LIMIT ");
            if (selectContext.getOffset() > 0) {
                sql.append(selectContext.getOffset()).append(" OFFSET ").append(selectContext.getRows());
            } else {
                sql.append(selectContext.getRows());
            }
        }
        return sql.toString();
    }

    private static PredicateSegment getTableOnWithQueryFilter(SelectContext selectContext, SqlTableInfo table) {
        return getSqlPredicateSegment(selectContext, table,table.hasOn()?table.getOn():null);
    }

    private static PredicateSegment getSqlWhereWithQueryFilter(SelectContext selectContext) {
        SqlTableInfo table = selectContext.getTable(0);
        return getSqlPredicateSegment(selectContext, table,selectContext.hasWhere()?selectContext.getWhere():null);
    }

    private static PredicateSegment getSqlPredicateSegment(SelectContext selectContext, SqlTableInfo table,PredicateSegment originalPredicate) {
        PredicateSegment predicateSegment=null;
        SqlExpression<SqlPredicate<?>> queryFilterExpression = table.getQueryFilterExpression();
        if (queryFilterExpression != null) {
            predicateSegment  = new AndPredicateSegment(true);
            EasyQueryLambdaFactory easyQueryLambdaFactory = selectContext.getRuntimeContext().getEasyQueryLambdaFactory();
            SqlPredicate<?> sqlPredicate = easyQueryLambdaFactory.createSqlPredicate(table.getIndex(), selectContext, predicateSegment);
            queryFilterExpression.apply(sqlPredicate);
            if(originalPredicate!=null&&originalPredicate.isNotEmpty()){
                predicateSegment.addPredicateSegment(originalPredicate);
            }
        }else{
            predicateSegment=originalPredicate;
        }
        return predicateSegment;
    }

    public static String toInsertSql(InsertContext insertContext) {

        int tableCount = insertContext.getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        SqlTableInfo table = insertContext.getTable(0);
        String tableName = table.getEntityMetadata().getTableName();
        sql.append(tableName).append(" (").append(insertContext.getColumns().toSql()).append(") VALUES (");
        int size = insertContext.getColumns().getSqlSegments().size();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                sql.append("?");
            } else {
                sql.append(",?");
            }
        }
        sql.append(") ");
        return sql.toString();
    }

    public static String toUpdateEntitySql(UpdateContext updateContext) {

        //将条件参数清空
        if (!updateContext.getProperties().isEmpty()) {
            updateContext.getProperties().clear();
        }
        int tableCount = updateContext.getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
        if (updateContext.getWhereColumns().isEmpty()) {
            throw new EasyQueryException("更新需要指定条件列");
        }

        StringBuilder sql = new StringBuilder("UPDATE ");
        SqlTableInfo table = updateContext.getTable(0);
        String tableName = table.getEntityMetadata().getTableName();
        sql.append(tableName).append(" SET ").append(updateContext.getSetColumns().toSql());
        sql.append(" WHERE ");
        sql.append(updateContext.getWhereColumns().toSql());
        return sql.toString();
    }

    public static String toUpdateExpressionSql(UpdateContext updateContext) {

        //将条件参数清空
        if (!updateContext.getParameters().isEmpty()) {
            updateContext.getParameters().clear();
        }
        int tableCount = updateContext.getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
        if (updateContext.getWhere().isEmpty()) {
            throw new EasyQueryException("更新需要设置条件");
        }

        StringBuilder sql = new StringBuilder("UPDATE ");
        SqlTableInfo table = updateContext.getTable(0);
        String tableName = table.getEntityMetadata().getTableName();
        sql.append(tableName).append(" SET ").append(updateContext.getSetColumns().toSql());
        sql.append(" WHERE ").append(updateContext.getWhere().getSql());
        return sql.toString();
    }
}
