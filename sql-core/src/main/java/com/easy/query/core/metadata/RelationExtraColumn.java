package com.easy.query.core.metadata;

/**
 * create time 2024/4/13 09:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationExtraColumn {
    private final String propertyName;
    private final String columnName;
    private final ColumnMetadata columnMetadata;
    private final boolean appendRelationExtra;

    public RelationExtraColumn(String propertyName,String columnName,ColumnMetadata columnMetadata,boolean appendRelationExtra){
        this.propertyName = propertyName;
        this.columnName = columnName;

        this.columnMetadata = columnMetadata;
        this.appendRelationExtra = appendRelationExtra;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getColumnName() {
        return columnName;
    }

    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    public boolean isAppendRelationExtra() {
        return appendRelationExtra;
    }
}
