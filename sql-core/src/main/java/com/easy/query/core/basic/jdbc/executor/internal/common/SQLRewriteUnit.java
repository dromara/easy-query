package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;

/**
 * create time 2023/5/19 10:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLRewriteUnit {
    void rewriteTableName(EntityTableSQLExpression entityTableSQLExpression);
}
