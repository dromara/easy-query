package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.executor.ExecutorContext0;
import com.easy.query.core.expression.sql.EntityExpression;

/**
 * create time 2023/4/9 22:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyPrepareParser {
    PrepareParseResult parse(EntityExpression entityExpression);
}
