package com.easy.query.core.func.column.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnNameExpression;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2025/11/27 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnNameExpressionImpl implements ColumnNameExpression {
    private final TableAvailable table;
    private final String columnName;

    public ColumnNameExpressionImpl(@NotNull TableAvailable table,@NotNull String columnName){
        this.table = table;
        this.columnName = columnName;
    }
    @NotNull
    @Override
    public TableAvailable getTableNotNull() {
        return table;
    }

    @NotNull
    @Override
    public String getColumnName() {
        return columnName;
    }
}
