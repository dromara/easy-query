package com.easy.query.core.basic.jdbc.parameter;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;

import java.sql.JDBCType;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/2/28 20:47
 */
public final class EasyColumnConstSQLParameter implements ConstSQLParameter {
    private final String propertyName;
    private final ColumnMetadata columnMetadata;
    private final Object val;
    private final TableAvailable entityTable;

    public EasyColumnConstSQLParameter(TableAvailable entityTable, String propertyName, ColumnMetadata columnMetadata, Object val) {
        this.entityTable = entityTable;
        this.propertyName = propertyName;
        this.columnMetadata = columnMetadata;
        this.val = val;
    }

    @Override
    public TableAvailable getTableOrNull() {
        return entityTable;
    }

    @Override
    public String getPropertyNameOrNull() {
        return propertyName;
    }

    @Override
    public Object getValue() {
        return val;
    }

    @Override
    public JDBCType getJdbcType() {
        if (columnMetadata != null) {
            return columnMetadata.getJdbcType();
        }
        return ConstSQLParameter.super.getJdbcType();
    }
}
