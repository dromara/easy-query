package com.easy.query.core.proxy.func.column;

import com.easy.query.core.expression.parser.core.SQLTableOwner;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

/**
 * create time 2025/11/27 13:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnParameter {

    private final SQLTableOwner tableOwner;
    private final String columnName;

    public ColumnParameter(@NotNull SQLTableOwner tableOwner,@NotNull String columnName){
        this.tableOwner = tableOwner;
        this.columnName = columnName;
    }

    public SQLTableOwner getTableOwner() {
        return tableOwner;
    }

    public String getColumnName() {
        return columnName;
    }
}
