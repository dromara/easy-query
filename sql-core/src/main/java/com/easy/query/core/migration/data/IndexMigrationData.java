package com.easy.query.core.migration.data;

import java.util.Collections;
import java.util.List;

/**
 * create time 2025/8/29 20:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class IndexMigrationData {

    private String indexName;
    private boolean unique;
    private List<EntityField> fields;


    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public List<EntityField> getFields() {
        if (fields == null) {
            return Collections.emptyList();
        }
        return fields;
    }

    public void setFields(List<EntityField> fields) {
        this.fields = fields;
    }

    public static class EntityField {
        private String fieldName;
        private String columnName;
        private boolean asc;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public boolean isAsc() {
            return asc;
        }

        public void setAsc(boolean asc) {
            this.asc = asc;
        }
    }
}
