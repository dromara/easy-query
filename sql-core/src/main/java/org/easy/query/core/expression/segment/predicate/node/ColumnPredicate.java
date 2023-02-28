package org.easy.query.core.expression.segment.predicate.node;

import org.easy.query.core.basic.api.context.SqlContext;
import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnPredicate implements Predicate {
    private final SqlTableInfo table;
    private final String propertyName;
    private final SqlPredicateCompare compare;
    private final SqlContext sqlContext;

    public ColumnPredicate(SqlTableInfo table, String propertyName, SqlPredicateCompare compare, SqlContext sqlContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.compare = compare;
        this.sqlContext = sqlContext;
    }

    @Override
    public String getSql() {
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(table,propertyName);
        return sqlColumnSegment +" "+ compare.getSql();
    }
}
