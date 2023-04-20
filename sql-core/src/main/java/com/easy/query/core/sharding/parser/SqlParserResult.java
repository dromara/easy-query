package com.easy.query.core.sharding.parser;

import com.easy.query.core.expression.sql.EntityExpression;

/**
 * create time 2023/4/18 23:15
 * 文件说明
 *
 * @author xuejiaming
 */
public final class SqlParserResult {
    private final EntityExpression entityExpression;

    public SqlParserResult(EntityExpression entityExpression){

        this.entityExpression = entityExpression;
    }

    public EntityExpression getEntityExpression() {
        return entityExpression;
    }
}
