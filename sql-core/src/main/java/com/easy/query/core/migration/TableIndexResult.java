package com.easy.query.core.migration;

import java.util.List;

/**
 * create time 2025/4/30 22:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableIndexResult {
    public final String indexName;
    public final boolean unique;
    public final List<EntityField> fields;

    public TableIndexResult(String indexName, boolean unique, List<EntityField> fields) {
        this.indexName = indexName;
        this.unique = unique;
        this.fields = fields;
    }

    public static class EntityField {
        public final String fieldName;
        public final String columnName;
        public final boolean asc;

        public EntityField(String fieldName, String columnName, boolean asc) {
            this.fieldName = fieldName;
            this.columnName = columnName;
            this.asc = asc;
        }
    }
}
