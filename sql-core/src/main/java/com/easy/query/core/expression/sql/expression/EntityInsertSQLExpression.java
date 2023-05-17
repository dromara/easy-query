package com.easy.query.core.expression.sql.expression;

import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;

/**
 * create time 2023/4/22 22:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityInsertSQLExpression extends EntitySQLExpression {
    SQLBuilderSegment getColumns();

    @Override
    EntityInsertSQLExpression cloneSQLExpression();
}
