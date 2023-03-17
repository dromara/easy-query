package com.easy.query.core.abstraction.metadata;

import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: LogicDeleteMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/26 23:19
 * @Created by xuejiaming
 */
public final class LogicDeleteMetadata {

    public LogicDeleteMetadata(String propertyName,SqlExpression<SqlPredicate<Object>> logicDeleteQueryFilterExpression, SqlExpression<SqlColumnSetter<Object>> logicDeletedSqlExpression) {
        this.propertyName = propertyName;
        this.logicDeleteQueryFilterExpression = logicDeleteQueryFilterExpression;
        this.logicDeletedSqlExpression = logicDeletedSqlExpression;
    }

    private final String propertyName;
    private final SqlExpression<SqlPredicate<Object>> logicDeleteQueryFilterExpression;
    private final SqlExpression<SqlColumnSetter<Object>> logicDeletedSqlExpression;

    public SqlExpression<SqlPredicate<Object>> getLogicDeleteQueryFilterExpression() {
        return logicDeleteQueryFilterExpression;
    }

    public SqlExpression<SqlColumnSetter<Object>> getLogicDeletedSqlExpression() {
        return logicDeletedSqlExpression;
    }

    public String getPropertyName() {
        return propertyName;
    }
}