package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.impl.AbstractSQLNativeSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/5 12:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeColumnSetPredicate extends AbstractSQLNativeSegmentImpl implements Predicate {

    private final Column2Segment column2Segment;

    public SQLNativeColumnSetPredicate(Column2Segment column2Segment, ExpressionContext expressionContext, String sqlSegment, SQLNativeExpression sqlNativeExpression) {
        super(expressionContext, sqlSegment, sqlNativeExpression);
        this.column2Segment = column2Segment;
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
        return new SQLNativeColumnSetPredicate(column2Segment,expressionContext,sqlSegment, sqlNativeExpression);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return SQLPredicateCompareEnum.EQ;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return column2Segment.toSQL(toSQLContext) +" "+getOperator().getSQL()+" "+ super.toSQL(toSQLContext);
    }
}
