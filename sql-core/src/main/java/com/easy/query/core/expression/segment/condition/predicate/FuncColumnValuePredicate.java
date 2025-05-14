package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

/**
 * @FileName: FuncColumnValuePredicate.java
 * @Description: column加方法函数和对应的值比较
 * create time 2023/2/14 23:34
 * @author xuejiaming
 */
public class FuncColumnValuePredicate implements Predicate {
    private final ColumnFunction func;
    private final SQLPredicateCompare compare;
    private final Column2Segment column2Segment;
    private final ColumnValue2Segment columnValue2Segment;

    public FuncColumnValuePredicate(Column2Segment column2Segment, ColumnFunction func, ColumnValue2Segment columnValue2Segment, SQLPredicateCompare compare) {
        this.column2Segment = column2Segment;
        this.columnValue2Segment = columnValue2Segment;
        this.func = func;
        this.compare = compare;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return func.getFuncColumn(column2Segment.toSQL(toSQLContext)) +" "+ compare.getSQL() + " "+columnValue2Segment.toSQL(toSQLContext);
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

        throw new UnsupportedOperationException();
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }
}
