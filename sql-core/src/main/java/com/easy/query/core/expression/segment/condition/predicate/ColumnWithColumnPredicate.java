package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.visitor.TableVisitor;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

/**
 * @author xuejiaming
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * create time 2023/2/14 23:34
 */
public class ColumnWithColumnPredicate implements Predicate {
    private final Column2Segment leftColumn2Segment;
    private final Column2Segment rightColumn2Segment;
    private final SQLPredicateCompare compare;

    public ColumnWithColumnPredicate(Column2Segment leftColumn2Segment, Column2Segment rightColumn2Segment, SQLPredicateCompare compare) {
        this.leftColumn2Segment = leftColumn2Segment;
        this.rightColumn2Segment = rightColumn2Segment;
        this.compare = compare;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return leftColumn2Segment.toSQL(toSQLContext) + " " + compare.getSQL() + " " + rightColumn2Segment.toSQL(toSQLContext);
    }

    @Override
    public TableAvailable getTable() {
        return leftColumn2Segment.getTable();
    }

    @Override
    public String getPropertyName() {
        return leftColumn2Segment.getColumnMetadata().getPropertyName();
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new ColumnWithColumnPredicate(leftColumn2Segment, rightColumn2Segment, compare);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public void accept(TableVisitor visitor) {
        visitor.visit(leftColumn2Segment.getTable());
        visitor.visit(rightColumn2Segment.getTable());
    }
}
