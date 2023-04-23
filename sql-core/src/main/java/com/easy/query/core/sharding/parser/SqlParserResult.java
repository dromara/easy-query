package com.easy.query.core.sharding.parser;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;

/**
 * create time 2023/4/18 23:15
 * 文件说明
 *
 * @author xuejiaming
 */
public final class SqlParserResult {
    private final EasyEntitySqlExpression easyEntitySqlExpression;

    public SqlParserResult(EasyEntitySqlExpression easyEntitySqlExpression){

        this.easyEntitySqlExpression = easyEntitySqlExpression;
    }

    public EasyEntitySqlExpression getEntitySqlExpression() {
        return easyEntitySqlExpression;
    }
}
