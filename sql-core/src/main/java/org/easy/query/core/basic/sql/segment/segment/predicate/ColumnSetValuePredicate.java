package org.easy.query.core.basic.sql.segment.segment.predicate;

import org.easy.query.core.basic.api.context.SqlColumnPredicateContext;
import org.easy.query.core.basic.api.context.SqlPredicateContext;
import org.easy.query.core.basic.api.context.UpdateContext;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnSetValuePredicate implements Predicate {
    private final int index;
    private final String column;

    private final String propertyName;
    private final Object val;
    private final SqlPredicateContext sqlContext;

    public ColumnSetValuePredicate(int index, String column, String propertyName, Object val, SqlPredicateContext sqlContext) {
        this.index = index;
        this.column = column;
        this.propertyName = propertyName;
        this.val = val;
        this.sqlContext = sqlContext;
    }

    @Override
    public String getSql() {
        sqlContext.addParameter(val);
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(index,column);
        return  sqlColumnSegment + " = ?";
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getVal() {
        return val;
    }
}
