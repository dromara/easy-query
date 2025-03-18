package com.easy.query.test.mssql;

import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilder;
import com.easy.query.core.extension.casewhen.SQLCaseWhenBuilderFactory;
import com.easy.query.mssql.config.MsSQLCaseWhenBuilder;

/**
 * create time 2025/3/18 20:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyMsSQLCaseWhenBuilderFactory implements SQLCaseWhenBuilderFactory {
    @Override
    public SQLCaseWhenBuilder create(ExpressionContext expressionContext) {
        return new MyMsSQLCaseWhenBuilder(expressionContext);
    }
}
