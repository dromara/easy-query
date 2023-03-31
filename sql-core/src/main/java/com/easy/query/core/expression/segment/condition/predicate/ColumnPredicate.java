package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.enums.SqlPredicateCompare;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @author xuejiaming
 */
public class ColumnPredicate implements Predicate {
    private final EntityTableExpression table;
    private final String propertyName;
    private final SqlPredicateCompare compare;
    private final EntityExpression sqlEntityExpression;

    public ColumnPredicate(EntityTableExpression table, String propertyName, SqlPredicateCompare compare, EntityExpression sqlEntityExpression) {
        this.table = table;
        this.propertyName = propertyName;
        this.compare = compare;
        this.sqlEntityExpression = sqlEntityExpression;
    }

    @Override
    public String toSql() {
        String sqlColumnSegment = sqlEntityExpression.getSqlOwnerColumn(table,propertyName);
        return sqlColumnSegment +" "+ compare.getSql();
    }

    @Override
    public EntityTableExpression getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }
}
