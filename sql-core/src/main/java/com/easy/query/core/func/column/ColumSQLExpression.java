package com.easy.query.core.func.column;

import com.easy.query.core.expression.segment.SQLSegment;

/**
 * create time 2023/10/18 09:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumSQLExpression extends ColumnExpression {
    SQLSegment getSQLSegment();
}
