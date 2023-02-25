package org.easy.query.core.segments.predicate;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.impl.SqlPredicateContext;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnValuePredicate implements Predicate {
    private final int index;
    private final String column;
    private final Object val;
    private final SqlSegment compare;
    private final SqlPredicateContext sqlPredicateContext;

    public ColumnValuePredicate(int index, String column, Object val, SqlSegment compare, SqlPredicateContext sqlPredicateContext) {
        this.index = index;
        this.column = column;
        this.val = val;
        this.compare = compare;
        this.sqlPredicateContext = sqlPredicateContext;
    }

    @Override
    public String getSql() {
        sqlPredicateContext.addParameter(val);
        String sqlColumnSegment = sqlPredicateContext.getSqlColumnSegment(index,column);
        return sqlColumnSegment + " " + compare.getSql() + " ?";
    }
}
