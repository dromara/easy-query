package org.easy.query.core.basic.sql.segment.segment;

import org.easy.query.core.basic.api.context.SqlColumnPredicateContext;

/**
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 * @Created by xuejiaming
 */
public class ColumnPredicateSegment implements SqlSegment {
    private final int index;

    private final String columnName;

    private final String propertyName;
    private final SqlColumnPredicateContext sqlColumnPredicateContext;

    public ColumnPredicateSegment(int index, String columnName, String propertyName, SqlColumnPredicateContext sqlColumnPredicateContext){
        this.index = index;
        this.columnName = columnName;
        this.propertyName = propertyName;
        this.sqlColumnPredicateContext = sqlColumnPredicateContext;
    }

    @Override
    public String getSql() {
        sqlColumnPredicateContext.addColumnProperty(propertyName);
        String sqlColumnSegment = sqlColumnPredicateContext.getSqlColumnSegment(index,columnName);
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
