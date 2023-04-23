package com.easy.query.core.sharding.parser;

import com.easy.query.core.expression.sql.expression.EasySqlExpression;

/**
 * create time 2023/4/18 23:15
 * 文件说明
 *
 * @author xuejiaming
 */
public final class SqlParserResult {
    private final EasySqlExpression sqlExpression;

    public SqlParserResult(EasySqlExpression sqlExpression){

        this.sqlExpression = sqlExpression;
    }

    public EasySqlExpression getSqlExpression() {
        return sqlExpression;
    }
}
