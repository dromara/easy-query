package com.easy.query.test.provider;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;

/**
 * create time 2023/10/28 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MySQLProvider<T> {
    WherePredicate<T> getWherePredicate();
    default MySQLProvider<T> findInSet(SQLActionExpression1<SQLNativePropertyExpressionContext> first, SQLActionExpression1<SQLNativePropertyExpressionContext> second){
        getWherePredicate().sqlNativeSegment("FIND_IN_SET({0},{1})",c->{
            first.apply(c);
            second.apply(c);
        });
        return this;
    }
}
