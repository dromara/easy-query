package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.context.PrepareParseContext;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

import java.util.List;

/**
 * create time 2023/4/9 22:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyPrepareParser {
    PrepareParseResult parse(PrepareParseContext prepareParseContext);
}
