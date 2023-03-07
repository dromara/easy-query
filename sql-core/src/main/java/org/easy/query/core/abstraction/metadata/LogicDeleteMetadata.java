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

    public LogicDeleteMetadata(SqlExpression<SqlPredicate<?>> logicDeleteQueryFilterExpression, SqlExpression<SqlColumnSetter<?>> logicDeletedSqlExpression) {
        this.logicDeleteQueryFilterExpression = logicDeleteQueryFilterExpression;
        this.logicDeletedSqlExpression = logicDeletedSqlExpression;
    }

    private final SqlExpression<SqlPredicate<?>> logicDeleteQueryFilterExpression;
    private final SqlExpression<SqlColumnSetter<?>> logicDeletedSqlExpression;

    public SqlExpression<SqlPredicate<?>> getLogicDeleteQueryFilterExpression() {
        return logicDeleteQueryFilterExpression;
    }

    public SqlExpression<SqlColumnSetter<?>> getLogicDeletedSqlExpression() {
        return logicDeletedSqlExpression;
    }
}
