package com.easy.query.core.migration.data;

import java.util.Collections;
import java.util.List;

/**
 * create time 2025/8/29 20:00
 * 索引迁移数据
 *
 * @author xuejiaming
 */
public class IndexMigrationData {

    /**
     * 索引名称
     */
    private String indexName;
    /**
     * 是否唯一索引
     */
    private boolean unique;
    /**
     * 索引字段
     */
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
        /**
         * 索引列名
         */
        private String columnName;
        /**
         * 是否升序
         */
        private boolean asc;

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
