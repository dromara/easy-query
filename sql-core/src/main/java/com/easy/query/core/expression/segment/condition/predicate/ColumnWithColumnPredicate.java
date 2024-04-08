package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnWithColumnPredicate implements Predicate {
    private final TableAvailable leftTable;
    private final String leftPropertyName;
    private final TableAvailable rightTable;
    private final String rightPropertyName;
    private final SQLPredicateCompare compare;
    private final ExpressionContext expressionContext;

    public ColumnWithColumnPredicate(TableAvailable leftTable, String leftPropertyName, TableAvailable rightTable, String rightPropertyName, SQLPredicateCompare compare, ExpressionContext expressionContext) {
        this.leftTable = leftTable;
        this.leftPropertyName = leftPropertyName;
        this.rightTable = rightTable;
        this.rightPropertyName = rightPropertyName;
        this.compare = compare;
        this.expressionContext = expressionContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        ColumnMetadata leftColumnMetadata = leftTable.getEntityMetadata().getColumnNotNull(leftPropertyName);
        String sqlColumnSegment1 = EasySQLExpressionUtil.getSQLOwnerColumn(expressionContext.getRuntimeContext(), leftTable, leftColumnMetadata.getName(), toSQLContext);
        ColumnMetadata rightColumnMetadata = rightTable.getEntityMetadata().getColumnNotNull(rightPropertyName);
        String sqlColumnSegment2 = EasySQLExpressionUtil.getSQLOwnerColumn(expressionContext.getRuntimeContext(), rightTable, rightColumnMetadata.getName(), toSQLContext);
        return sqlColumnSegment1 +" "+ compare.getSQL() + " "+sqlColumnSegment2;
    }

    @Override
    public TableAvailable getTable() {
        return leftTable;
    }

    @Override
    public String getPropertyName() {
        return leftPropertyName;
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
