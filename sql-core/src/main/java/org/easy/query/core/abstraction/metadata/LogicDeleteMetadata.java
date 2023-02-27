package org.easy.query.core.abstraction.metadata;

import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: LogicDeleteMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/26 23:19
 * @Created by xuejiaming
 */
public final class LogicDeleteMetadata {

    public LogicDeleteMetadata(SqlExpression<? extends SqlPredicate<?>> defaultSqlExpression, SqlExpression<? extends SqlPredicate<?>> deletedSqlExpression) {
        this.defaultSqlExpression = defaultSqlExpression;
        this.deletedSqlExpression = deletedSqlExpression;
    }

    private final SqlExpression<? extends SqlPredicate<?>> defaultSqlExpression;
    private final SqlExpression<? extends SqlPredicate<?>> deletedSqlExpression;

    public SqlExpression<? extends SqlPredicate<?>> getDefaultSqlExpression() {
        return defaultSqlExpression;
    }

    public SqlExpression<? extends SqlPredicate<?>> getDeletedSqlExpression() {
        return deletedSqlExpression;
    }
}
