package com.easy.query.mssql.config;

import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilder;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilderFactory;

/**
 * create time 2025/3/18 20:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLCaseWhenBuilderFactory implements SQLCaseWhenBuilderFactory {
    @Override
    public SQLCaseWhenBuilder create(ExpressionContext expressionContext) {
        return new MsSQLCaseWhenBuilder(expressionContext);
    }
}
