package org.easy.query.core.segments.predicate;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;

/**
 * @FileName: ColumnValuePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/14 23:34
 * @Created by xuejiaming
 */
public class ColumnPredicate implements Predicate {
    private final int index;
    private final String column;
    private final SqlSegment compare;
    private final SelectContext selectContext;

    public ColumnPredicate(int index, String column, SqlSegment compare, SelectContext selectContext) {

        this.index = index;
        this.column = column;
        this.compare = compare;
        this.selectContext = selectContext;
    }

    @Override
    public String getSql() {
        SelectTableInfo table = selectContext.getTable(index);
        String quoteName = selectContext.getQuoteName(column);
        return table.getAlias() + "." + quoteName +" "+ compare.getSql();
    }
}
