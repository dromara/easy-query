package org.easy.query.core.expression.segment.condition.predicate;

import org.easy.query.core.expression.context.SqlContext;
import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnWithColumnPredicate implements Predicate {
    private final SqlTableInfo leftTable;
    private final String leftPropertyName;
    private final SqlTableInfo rightTable;
    private final String rightPropertyName;
    private final SqlPredicateCompare compare;
    private final SqlContext sqlContext;

    public ColumnWithColumnPredicate(SqlTableInfo leftTable, String leftPropertyName, SqlTableInfo rightTable, String rightPropertyName, SqlPredicateCompare compare, SqlContext sqlContext) {
        this.leftTable = leftTable;
        this.leftPropertyName = leftPropertyName;
        this.rightTable = rightTable;
        this.rightPropertyName = rightPropertyName;
        this.compare = compare;
        this.sqlContext = sqlContext;
    }

    @Override
    public String toSql() {
        String sqlColumnSegment1 = sqlContext.getSqlColumnSegment(leftTable,leftPropertyName);
        String sqlColumnSegment2 = sqlContext.getSqlColumnSegment(rightTable,rightPropertyName);
        return sqlColumnSegment1 +" "+ compare.getSql() + " "+sqlColumnSegment2;
    }
}
