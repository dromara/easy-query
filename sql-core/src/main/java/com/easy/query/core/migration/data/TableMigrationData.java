package com.easy.query.core.migration.data;

import java.util.List;

/**
 * create time 2025/8/21 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class TableMigrationData {
    private String schema;
    private String name;
    private String comment;

    private List<ColumnMigrationData> columns;
}
