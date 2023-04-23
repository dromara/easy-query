package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;

/**
 * create time 2023/4/20 13:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class RewriteContext {
    private final EasyEntitySqlExpression originalEntitySqlExpression;
    private final EasyEntitySqlExpression entitySqlExpression;

    public RewriteContext(EasyEntitySqlExpression originalEntitySqlExpression, EasyEntitySqlExpression entitySqlExpression){
        this.originalEntitySqlExpression = originalEntitySqlExpression;
        this.entitySqlExpression = entitySqlExpression;
    }

    public EasyEntitySqlExpression getOriginalEntitySqlExpression() {
        return originalEntitySqlExpression;
    }

    public EasyEntitySqlExpression getEntitySqlExpression() {
        return entitySqlExpression;
    }
}
