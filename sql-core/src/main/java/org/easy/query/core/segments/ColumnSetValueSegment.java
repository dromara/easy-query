package org.easy.query.core.segments;

import org.easy.query.core.impl.UpdateContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.segments.predicate.Predicate;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnSetValueSegment implements Predicate {
    private final int index;
    private final String column;

    private final String propertyName;
    private final Object val;
    private final UpdateContext updateContext;

    public ColumnSetValueSegment(int index, String column, String propertyName, Object val, UpdateContext updateContext) {
        this.index = index;
        this.column = column;
        this.propertyName = propertyName;
        this.val = val;
        this.updateContext = updateContext;
    }

    @Override
    public String getSql() {
        String sqlColumnSegment = updateContext.getSqlColumnSegment(index,column);
        return  sqlColumnSegment + " = ?";
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getVal() {
        return val;
    }
}
