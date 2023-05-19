package com.easy.query.core.expression.executor.parser.context;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

import java.util.List;

/**
 * create time 2023/5/18 21:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PrepareParseContext {
    ExecutorContext getExecutorContext();
    EntityExpressionBuilder getEntityExpressionBuilder();
}
