package com.easy.query.core.metadata;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * @FileName: LogicDeleteMetadata.java
 * @Description: 文件说明
 * create time 2023/2/26 23:19
 * @author xuejiaming
 */
public final class LogicDeleteMetadata {

    public LogicDeleteMetadata(String propertyName, SQLActionExpression1<WherePredicate<Object>> logicDeletePredicateFilterExpression, SQLActionExpression1<ColumnSetter<Object>> logicDeletedSQLExpression) {
        this.propertyName = propertyName;
        this.logicDeletePredicateFilterExpression = logicDeletePredicateFilterExpression;
        this.logicDeletedSQLExpression = logicDeletedSQLExpression;
    }

    private final String propertyName;
    /**
     * where
     * 查询 更新 删除等操作会添加该表达式
     */
    private final SQLActionExpression1<WherePredicate<Object>> logicDeletePredicateFilterExpression;
    /**
     * update set
     * 删除时执行设置表字段信息
     */
    private final SQLActionExpression1<ColumnSetter<Object>> logicDeletedSQLExpression;

    public SQLActionExpression1<WherePredicate<Object>> getLogicDeletePredicateFilterExpression() {
        return logicDeletePredicateFilterExpression;
    }

    public SQLActionExpression1<ColumnSetter<Object>> getLogicDeletedSQLExpression() {
        return logicDeletedSQLExpression;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
