package com.easy.query.test.provider;

import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContext;
import com.easy.query.core.expression.lambda.SQLActionExpression1;

/**
 * create time 2023/10/28 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MySQLLambdaProvider<T> {
    SQLWherePredicate<T> getSQLWherePredicate();
    default MySQLLambdaProvider<T> findInSet(SQLActionExpression1<SQLNativeLambdaExpressionContext<T>> first, SQLActionExpression1<SQLNativeLambdaExpressionContext<T>> second){
        getSQLWherePredicate().sqlNativeSegment("FIND_IN_SET({0},{1})",c->{
            first.apply(c);
            second.apply(c);
        });
        return this;
    }
}
