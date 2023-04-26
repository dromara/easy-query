package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

import java.util.List;

/**
 * create time 2023/4/25 17:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityPrepareParseResult extends PrepareParseResult{
    List<Object> getEntities();

    @Override
    EntityExpressionBuilder getEntityExpressionBuilder();
}
