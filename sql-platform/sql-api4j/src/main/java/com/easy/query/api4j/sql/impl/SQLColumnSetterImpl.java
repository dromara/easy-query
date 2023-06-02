package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLColumnSetter;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 */
public class SQLColumnSetterImpl<T> implements SQLColumnSetter<T> {
    private final ColumnSetter<T> columnSetter;

    public SQLColumnSetterImpl(ColumnSetter<T> columnSetter) {
        this.columnSetter = columnSetter;
    }

    @Override
    public ColumnSetter<T> getColumnSetter() {
        return columnSetter;
    }
}
