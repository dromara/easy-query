package com.easy.query.core.extension.casewhen;

import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2025/3/18 20:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLCaseWhenBuilderFactory implements SQLCaseWhenBuilderFactory{
    @Override
    public SQLCaseWhenBuilder create(ExpressionContext expressionContext) {
        return new DefaultCaseWhenBuilder(expressionContext);
    }
}
