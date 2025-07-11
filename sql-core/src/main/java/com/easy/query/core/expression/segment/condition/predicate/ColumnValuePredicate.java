package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.ConstLikeSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
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
 * @FileName: ColumnValuePredicate.java
 * @Description: colum和具体值的断言
 * create time 2023/2/14 23:34
 */
public class ColumnValuePredicate implements ValuePredicate, ShardingPredicate {
    private final Column2Segment column2Segment;
    private final ColumnValue2Segment columnValue2Segment;
    private final SQLPredicateCompare compare;

    public ColumnValuePredicate(Column2Segment column2Segment, ColumnValue2Segment columnValue2Segment, SQLPredicateCompare compare) {
        this.column2Segment = column2Segment;
        this.columnValue2Segment = columnValue2Segment;
        this.compare = compare;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return column2Segment.toSQL(toSQLContext) + " " + compare.getSQL() + " " + columnValue2Segment.toSQL(toSQLContext);
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
        return new ColumnValuePredicate(column2Segment, columnValue2Segment, compare);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public SQLParameter getParameter() {
//        if (SQLPredicateCompareEnum.LIKE == compare || SQLPredicateCompareEnum.NOT_LIKE == compare) {
//            return new ConstLikeSQLParameter(columnValue2Segment.getSQLParameter());
//        }
        return columnValue2Segment.getSQLParameter();
    }
}
