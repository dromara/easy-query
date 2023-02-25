package org.easy.query.core.basic.sql.segment.segment;

import org.easy.query.core.impl.SqlContext;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @Created by xuejiaming
 */
public class UpdateColumnSegment implements SqlSegment {
    private final int index;

    public String getColumnName() {
        return columnName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public SqlContext getSqlContext() {
        return sqlContext;
    }

    private final String columnName;

    private final String propertyName;
    private final SqlContext sqlContext;

    public UpdateColumnSegment(int index, String columnName, String propertyName, SqlContext sqlContext){
        this.index = index;

        this.columnName = columnName;
        this.propertyName = propertyName;
        this.sqlContext = sqlContext;
    }

    @Override
    public String getSql() {
        String sqlColumnSegment = sqlContext.getSqlColumnSegment(index,columnName);
        return sqlColumnSegment + " = ?";
    }

    public int getIndex() {
        return index;
    }
}
