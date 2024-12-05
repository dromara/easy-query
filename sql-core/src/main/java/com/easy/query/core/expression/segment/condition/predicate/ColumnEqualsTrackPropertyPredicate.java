package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.PropertyTrackSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

/**
 * @author xuejiaming
 */
public class ColumnEqualsTrackPropertyPredicate implements Predicate, ValuePredicate {
    protected final SQLPredicateCompare sqlPredicateCompare;
    private final Column2Segment column2Segment;
    private final ColumnValue2Segment columnValue2Segment;

    public ColumnEqualsTrackPropertyPredicate(Column2Segment column2Segment, ColumnValue2Segment columnValue2Segment, SQLPredicateCompare sqlPredicateCompare) {
        this.column2Segment = column2Segment;
        this.columnValue2Segment = columnValue2Segment;
        this.sqlPredicateCompare = sqlPredicateCompare;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return column2Segment.toSQL(toSQLContext) + " " + sqlPredicateCompare.getSQL() + " " + columnValue2Segment.toSQL(toSQLContext);
    }

    @Override
    public TableAvailable getTable() {
        return column2Segment.getTable();
    }

    public String getPropertyName() {
        return column2Segment.getColumnMetadata().getPropertyName();
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new ColumnEqualsTrackPropertyPredicate(column2Segment,columnValue2Segment,sqlPredicateCompare);
    }


    @Override
    public SQLPredicateCompare getOperator() {
        return SQLPredicateCompareEnum.EQ;
    }

    @Override
    public SQLParameter getParameter() {
        return columnValue2Segment.getSQLParameter();
    }
}
