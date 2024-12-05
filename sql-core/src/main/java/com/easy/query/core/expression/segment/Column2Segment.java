package com.easy.query.core.expression.segment;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2024/12/5 10:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Column2Segment extends SQLSegment{
    TableAvailable getTable();
    ColumnMetadata getColumnMetadata();
    ExpressionContext getExpressionContext();
}
