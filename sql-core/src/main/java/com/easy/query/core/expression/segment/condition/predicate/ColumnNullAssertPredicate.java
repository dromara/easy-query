package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * create time 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnNullAssertPredicate implements Predicate {
    private final Column2Segment column2Segment;
    private final SQLPredicateCompare compare;

    public ColumnNullAssertPredicate(Column2Segment column2Segment, SQLPredicateCompare compare) {
        this.column2Segment = column2Segment;
        this.compare = compare;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return column2Segment.toSQL(toSQLContext) +" "+ compare.getSQL();
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
        return new ColumnNullAssertPredicate(column2Segment, compare);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }
}
