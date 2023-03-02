package org.easy.query.core.expression.segment.condition.predicate;

import org.easy.query.core.expression.context.SqlContext;
import org.easy.query.core.basic.jdbc.parameter.ConstSQLParameter;
import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.query.builder.SqlTableInfo;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: colum和具体值的断言
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnValuePredicate implements Predicate {
    private final SqlTableInfo table;
    private final String propertyName;
    private final Object val;
    private final SqlPredicateCompare compare;
    private final SqlContext sqlContext;

    public ColumnValuePredicate(SqlTableInfo table, String propertyName, Object val, SqlPredicateCompare compare, SqlContext sqlContext) {
        this.table = table;
        this.propertyName = propertyName;
        this.val = val;
        this.compare = compare;
        this.sqlContext = sqlContext;
    }

    @Override
    public String toSql() {
        sqlContext.addParameter(new ConstSQLParameter(table,propertyName,val));
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(table,propertyName);
        return sqlColumnSegment + " " + compare.getSql() + " ?";
    }
}
