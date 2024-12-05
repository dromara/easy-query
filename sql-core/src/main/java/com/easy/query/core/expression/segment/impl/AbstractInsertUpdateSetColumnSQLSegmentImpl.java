package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.DefaultSQLPropertyConverter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasySQLUtil;

import java.util.Objects;

/**
 * create time 2023/8/8 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractInsertUpdateSetColumnSQLSegmentImpl {

    protected final Column2Segment column2Segment;
    protected final ColumnValue2Segment columnValue2Segment;

    public AbstractInsertUpdateSetColumnSQLSegmentImpl(Column2Segment column2Segment, ColumnValue2Segment columnValue2Segment) {
        TableAvailable table = Objects.requireNonNull(column2Segment.getTable());
        ColumnMetadata columnMetadata = Objects.requireNonNull(column2Segment.getColumnMetadata());
        if (column2Segment.getColumnMetadata().isValueObject()) {
            throw new IllegalArgumentException("entity:[" + EasyClassUtil.getSimpleName(table.getEntityClass()) + "." + columnMetadata.getPropertyName() + "] is value object");
        }
        this.column2Segment = column2Segment;
        this.columnValue2Segment = columnValue2Segment;
    }
}
