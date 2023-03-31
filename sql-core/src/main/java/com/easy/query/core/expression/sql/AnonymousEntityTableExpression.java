package com.easy.query.core.expression.sql;

/**
 * create time 2023/3/31 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AnonymousEntityTableExpression extends EntityTableExpression{
    EntityQueryExpression getEntityQueryExpression();
}
