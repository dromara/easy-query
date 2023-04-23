package com.easy.query.core.expression.sql.builder;

/**
 * create time 2023/3/31 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AnonymousEntityTableExpressionBuilder extends EntityTableExpressionBuilder {
    EntityQueryExpressionBuilder getEntityQueryExpressionBuilder();
}
