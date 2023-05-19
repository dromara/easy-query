package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.descriptor.TableParseDescriptor;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

import java.util.Set;

/**
 * create time 2023/4/9 22:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PrepareParseResult {
    boolean isSharding();
    ExecutorContext getExecutorContext();
    TableParseDescriptor getTableParseDescriptor();
    EntityExpressionBuilder getEntityExpressionBuilder();

}
