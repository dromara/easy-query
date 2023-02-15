package org.easy.query.core.segments;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.abstraction.SqlSegment0;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @Created by xuejiaming
 */
public class ColumnSegment implements SqlSegment {
    private final int index;

    public String getColumnName() {
        return columnName;
    }

    public SelectContext getSelectContext() {
        return selectContext;
    }

    private final String columnName;
    private final SelectContext selectContext;
    private String alias;

    public ColumnSegment(int index, String columnName, SelectContext selectContext){
        this(index,columnName,selectContext,null);
    }
    public ColumnSegment(int index, String columnName, SelectContext selectContext,String alias){
        this.index = index;

        this.columnName = columnName;
        this.selectContext = selectContext;
        this.alias = alias;
    }

    @Override
    public String getSql() {
        SelectTableInfo table = selectContext.getTable(index);
        String quoteName = selectContext.getQuoteName(columnName);
        StringBuilder sql = new StringBuilder().append(table.getAlias()).append(".").append(quoteName);
        if(getAlias()!=null){
            sql.append(" AS ").append(selectContext.getQuoteName(getAlias()));
        }
        return sql.toString();
    }

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
