package com.easy.query.clickhouse.expression;

import com.easy.query.core.enums.QueryLockEnum;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.expression.sql.expression.impl.QuerySQLExpressionImpl;

/**
 * create time 2023/5/17 22:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClickHouseQuerySQLExpression extends QuerySQLExpressionImpl {
    public ClickHouseQuerySQLExpression(EntitySQLExpressionMetadata entitySQLExpressionMetadata) {
        super(entitySQLExpressionMetadata);
    }

    @Override
    protected String appendQueryLock(boolean root, String sql) {
        if (!root) {
            return sql;
        }
        if (entitySQLExpressionMetadata.getExpressionContext().getQueryLock() != QueryLockEnum.FOR_UPDATE) {
            return sql;
        }
        throw new UnsupportedOperationException("ClickHouse does not support FOR UPDATE row-level locking. " +
                "ClickHouse is optimized for OLAP workloads and does not provide row-level locks.");
    }
}
