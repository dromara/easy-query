package com.easy.query.core.proxy.core.accpet;

import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;

/**
 * create time 2023/12/8 15:31
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityExpressionAccept {
    void accept(SQLPredicateExpression sqlPredicateExpression);
    void accept(SQLAggregatePredicateExpression sqlAggregatePredicateExpression);
    void accept(SQLColumnSetExpression sqlColumnSetExpression);
    void accept(SQLOrderByExpression sqlOrderByExpression);
    EntityExpressionAccept empty=new UnsupportedOperationEntityExpressionAcceptImpl();
}
