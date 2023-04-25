package com.easy.query.core.expression.executor.parser;


import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

import java.util.List;

/**
 * create time 2023/4/24 22:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface InsertPrepareParseResult extends PrepareParseResult{
    List<Object> getEntities();

    @Override
    EntityInsertExpressionBuilder getEntityExpressionBuilder();
    boolean isFillAutoIncrement();
}
