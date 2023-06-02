package com.easy.query.core.metadata;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * @FileName: LogicDeleteMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/26 23:19
 * @author xuejiaming
 */
public final class LogicDeleteMetadata {

    public LogicDeleteMetadata(String propertyName, SQLExpression1<WherePredicate<Object>> logicDeletePredicateFilterExpression, SQLExpression1<ColumnSetter<Object>> logicDeletedSQLExpression) {
        this.propertyName = propertyName;
        this.logicDeletePredicateFilterExpression = logicDeletePredicateFilterExpression;
        this.logicDeletedSQLExpression = logicDeletedSQLExpression;
    }

    private final String propertyName;
    /**
     * where
     */
    private final SQLExpression1<WherePredicate<Object>> logicDeletePredicateFilterExpression;
    /**
     * update set
     */
    private final SQLExpression1<ColumnSetter<Object>> logicDeletedSQLExpression;

    public SQLExpression1<WherePredicate<Object>> getLogicDeletePredicateFilterExpression() {
        return logicDeletePredicateFilterExpression;
    }

    public SQLExpression1<ColumnSetter<Object>> getLogicDeletedSQLExpression() {
        return logicDeletedSQLExpression;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
