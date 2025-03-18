package com.easy.query.core.extension.casewhen;

import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2025/3/18 20:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLCaseWhenBuilderFactory {
    SQLCaseWhenBuilder create(ExpressionContext expressionContext);
}
