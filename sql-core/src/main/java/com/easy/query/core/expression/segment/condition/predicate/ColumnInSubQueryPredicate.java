package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/4/27 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnInSubQueryPredicate implements SubQueryPredicate {
    private final SQLPredicateCompare compare;
    private final ExpressionContext expressionContext;
    private final Query<?> subQuery;
    private final Column2Segment column2Segment;

    public ColumnInSubQueryPredicate(Column2Segment column2Segment, Query<?> subQuery, SQLPredicateCompare compare, ExpressionContext expressionContext) {
        this.column2Segment = column2Segment;
        this.subQuery = subQuery;
        this.compare = compare;
        this.expressionContext = expressionContext;
    }

    @Override
    public TableAvailable getTable() {
        return column2Segment.getTable();
    }

    @Override
    public String getPropertyName() {
        return column2Segment.getColumnMetadata().getPropertyName();
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new ColumnInSubQueryPredicate(column2Segment, subQuery.cloneQueryable(), compare, expressionContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlColumnSegment = column2Segment.toSQL(toSQLContext);
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
        return new ColumnInSubQueryPredicate(column2Segment, subQuery.cloneQueryable(), compare, expressionContext);
    }

    @Override
    public void accept(TableVisitor visitor) {
        SubQueryPredicate.super.accept(visitor);
        subQuery.getSQLEntityExpressionBuilder().accept(visitor);
    }
}
