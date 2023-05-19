package com.easy.query.core.expression.executor.parser.context;

import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

/**
 * create time 2023/5/18 21:27
 * 文件说明
 *
 * @author xuejiaming
 */
public interface InsertEntityParseContext extends EntityParseContext {
    @Override
    EntityInsertExpressionBuilder getEntityExpressionBuilder();

    boolean getFillAutoIncrement();
}
