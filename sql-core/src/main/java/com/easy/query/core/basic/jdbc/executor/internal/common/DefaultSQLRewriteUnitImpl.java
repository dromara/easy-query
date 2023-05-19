package com.easy.query.core.basic.jdbc.executor.internal.common;

import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;

/**
 * create time 2023/5/19 10:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLRewriteUnitImpl implements SQLRewriteUnit{
    public static final SQLRewriteUnit INSTANCE=new DefaultSQLRewriteUnitImpl();
    @Override
    public void rewriteTableName(EntityTableSQLExpression entityTableSQLExpression) {

    }
}
