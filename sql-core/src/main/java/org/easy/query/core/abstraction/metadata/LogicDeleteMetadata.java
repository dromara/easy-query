package org.easy.query.core.abstraction.metadata;

import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: LogicDeleteMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/26 23:19
 * @Created by xuejiaming
 */
public final class LogicDeleteMetadata {

    public LogicDeleteMetadata(SqlExpression<SqlPredicate<?>> queryFilterExpression, SqlExpression<SqlColumnSetter<?>> deletedSqlExpression) {
        this.queryFilterExpression = queryFilterExpression;
        this.deletedSqlExpression = deletedSqlExpression;
    }

    private final SqlExpression<SqlPredicate<?>> queryFilterExpression;
    private final SqlExpression<SqlColumnSetter<?>> deletedSqlExpression;

    public SqlExpression<SqlPredicate<?>> getQueryFilterExpression() {
        return queryFilterExpression;
    }

    public SqlExpression<SqlColumnSetter<?>> getDeletedSqlExpression() {
        return deletedSqlExpression;
    }
}
