package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;

/**
 * create time 2023/5/30 12:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnInsertSegment extends SQLEntitySegment{

    String toInsertColumn(ToSQLContext toSQLContext);
}
