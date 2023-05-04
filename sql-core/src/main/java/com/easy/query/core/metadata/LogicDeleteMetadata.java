package com.easy.query.core.metadata;

import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.core.SqlColumnSetter;
import com.easy.query.core.expression.parser.core.SqlPredicate;

/**
 * @FileName: LogicDeleteMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/26 23:19
 * @author xuejiaming
 */
public final class LogicDeleteMetadata {

    public LogicDeleteMetadata(String propertyName, SqlExpression<SqlPredicate<Object>> logicDeletePredicateFilterExpression, SqlExpression<SqlColumnSetter<Object>> logicDeletedSqlExpression) {
        this.propertyName = propertyName;
        this.logicDeletePredicateFilterExpression = logicDeletePredicateFilterExpression;
        this.logicDeletedSqlExpression = logicDeletedSqlExpression;
    }

    private final String propertyName;
    /**
     * where
     */
    private final SqlExpression<SqlPredicate<Object>> logicDeletePredicateFilterExpression;
    /**
     * update set
     */
    private final SqlExpression<SqlColumnSetter<Object>> logicDeletedSqlExpression;

    public SqlExpression<SqlPredicate<Object>> getLogicDeletePredicateFilterExpression() {
        return logicDeletePredicateFilterExpression;
    }

    public SqlExpression<SqlColumnSetter<Object>> getLogicDeletedSqlExpression() {
        return logicDeletedSqlExpression;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
