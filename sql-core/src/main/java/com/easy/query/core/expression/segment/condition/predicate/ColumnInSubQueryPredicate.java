package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/4/27 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnInSubQueryPredicate implements SubQueryPredicate{
    private final SQLPredicateCompare compare;
    private final ExpressionContext expressionContext;
    private final TableAvailable table;
    private final String propertyName;
    private final Query<?> subQuery;

    public ColumnInSubQueryPredicate(TableAvailable table, String propertyName, Query<?> subQuery, SQLPredicateCompare compare, ExpressionContext expressionContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.subQuery = subQuery;
        this.compare = compare;
        this.expressionContext = expressionContext;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyName);
        String sqlColumnSegment = EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, table, columnMetadata, toSQLContext,true,false);
        StringBuilder sql = new StringBuilder();
        sql.append(sqlColumnSegment).append(" ").append(compare.getSQL()).append(" (");
        String subQueryableSQL = subQuery.toSQL(toSQLContext);
        sql.append(subQueryableSQL).append(")");
        return sql.toString();
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public Query<?> getSubQuery() {
        return subQuery;
    }

    @Override
    public SubQueryPredicate cloneSubQueryPredicate() {
        return new ColumnInSubQueryPredicate(table, propertyName, subQuery.cloneQueryable(), compare, expressionContext);
    }
}
