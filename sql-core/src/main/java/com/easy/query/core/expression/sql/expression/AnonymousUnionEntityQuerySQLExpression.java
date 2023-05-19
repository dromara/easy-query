package com.easy.query.core.expression.sql.expression;

import java.util.List;

/**
 * create time 2023/5/19 11:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AnonymousUnionEntityQuerySQLExpression extends AnonymousEntityQuerySQLExpression{
    List<EntityQuerySQLExpression> getEntityQuerySQLExpressions();
}
