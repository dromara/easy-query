package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;

/**
 * create time 2023/4/9 22:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyPrepareParser {
    PrepareParseResult parse(EasyEntitySqlExpression easyEntitySqlExpression);
}
