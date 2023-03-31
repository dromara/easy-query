package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnWithColumnPredicate implements Predicate {
    private final EntityTableExpression leftTable;
    private final String leftPropertyName;
    private final EntityTableExpression rightTable;
    private final String rightPropertyName;
    private final SqlPredicateCompare compare;
    private final EntityExpression sqlEntityExpression;

    public ColumnWithColumnPredicate(EntityTableExpression leftTable, String leftPropertyName, EntityTableExpression rightTable, String rightPropertyName, SqlPredicateCompare compare, EntityExpression sqlEntityExpression) {
        this.leftTable = leftTable;
        this.leftPropertyName = leftPropertyName;
        this.rightTable = rightTable;
        this.rightPropertyName = rightPropertyName;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql() {
        String sqlColumnSegment1 = sqlEntityExpression.getSqlOwnerColumn(leftTable,leftPropertyName);
        String sqlColumnSegment2 = sqlEntityExpression.getSqlOwnerColumn(rightTable,rightPropertyName);
        return sqlColumnSegment1 +" "+ compare.getSql() + " "+sqlColumnSegment2;
    }

    @Override
    public EntityTableExpression getTable() {
        return leftTable;
    }

    @Override
    public String getPropertyName() {
        return leftPropertyName;
    }

}
