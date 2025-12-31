package com.easy.query.core.expression.predicate;

import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;

/**
 * create time 2025/12/31 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class SmartPredicateParseResult {
    public final SmartPredicateItem smartPredicateItem;
    public final SQLActionExpression1<Filter> filterAction;

    public SmartPredicateParseResult(SmartPredicateItem smartPredicateItem, SQLActionExpression1<Filter> filterAction) {
        this.smartPredicateItem = smartPredicateItem;
        this.filterAction = filterAction;
    }
}
