package org.easy.query.core.basic.sql.segment.segment;

import org.easy.query.core.impl.SqlContext;
import org.easy.query.core.impl.UpdateContext;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @Created by xuejiaming
 */
public class UpdateColumnSegment implements SqlSegment {
    private final int index;

    private final String columnName;

    private final String propertyName;
    private final UpdateContext updateContext;

    public UpdateColumnSegment(int index, String columnName, String propertyName, UpdateContext updateContext){
        this.index = index;
        this.columnName = columnName;
        this.propertyName = propertyName;
        this.updateContext = updateContext;
    }

    @Override
    public String getSql() {
        updateContext.addColumnProperty(propertyName);
        String sqlColumnSegment = updateContext.getSqlColumnSegment(index,columnName);
        return sqlColumnSegment + " = ?";
    }

    public int getIndex() {
        return index;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getPropertyName() {
        return propertyName;
    }

}
