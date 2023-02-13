package org.easy.query.core.segments;

import org.easy.query.core.abstraction.SqlSegment0;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @Created by xuejiaming
 */
public class ColumnSegment implements SqlSegment0 {
    private final int index;
    private final String columnName;
    private final SelectContext selectContext;
    private String alias;

    public ColumnSegment(int index, String columnName, SelectContext selectContext){
        this.index = index;

        this.columnName = columnName;
        this.selectContext = selectContext;
    }

    @Override
    public String toSql() {
        SelectTableInfo table = selectContext.getTable(index);
        String quoteName = selectContext.getQuoteName(columnName);

        return table.getAlias()+"."+quoteName;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
