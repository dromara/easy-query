package com.easy.query.core.metadata;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;

/**
 * @FileName: LogicDeleteMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/26 23:19
 * @author xuejiaming
 */
public final class LogicDeleteMetadata {

    public LogicDeleteMetadata(String propertyName, SQLExpression1<SQLWherePredicate<Object>> logicDeletePredicateFilterExpression, SQLExpression1<SQLColumnSetter<Object>> logicDeletedSQLExpression) {
        this.propertyName = propertyName;
        this.logicDeletePredicateFilterExpression = logicDeletePredicateFilterExpression;
        this.logicDeletedSQLExpression = logicDeletedSQLExpression;
    }

    private final String propertyName;
    /**
     * where
     */
    private final SQLExpression1<SQLWherePredicate<Object>> logicDeletePredicateFilterExpression;
    /**
     * update set
     */
    private final SQLExpression1<SQLColumnSetter<Object>> logicDeletedSQLExpression;

    public SQLExpression1<SQLWherePredicate<Object>> getLogicDeletePredicateFilterExpression() {
        return logicDeletePredicateFilterExpression;
    }

    public SQLExpression1<SQLColumnSetter<Object>> getLogicDeletedSQLExpression() {
        return logicDeletedSQLExpression;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
