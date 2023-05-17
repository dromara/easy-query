package com.easy.query.core.expression.sql.expression;

/**
 * create time 2023/4/23 07:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityDeleteSQLExpression extends EntityPredicateSQLExpression {

    @Override
    EntityDeleteSQLExpression cloneSQLExpression();
}
