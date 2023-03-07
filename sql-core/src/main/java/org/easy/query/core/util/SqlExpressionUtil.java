package org.easy.query.core.util;

import org.easy.query.core.basic.api.select.Queryable2;
import org.easy.query.core.expression.lambda.SqlExpression2;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: SqlExpressionUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/7 12:32
 * @Created by xuejiaming
 */
public class SqlExpressionUtil {
    private SqlExpressionUtil(){}
    public static  <T1,T2> Queryable2<T1, T2> executeJoinOn(Queryable2<T1, T2> queryable2, SqlExpression2<SqlPredicate<T1>, SqlPredicate<T2>> on){
        SqlPredicate<T1> sqlOnPredicate1 = queryable2.getSqlBuilderProvider2().getSqlOnPredicate1();
        SqlPredicate<T2> sqlOnPredicate2 = queryable2.getSqlBuilderProvider2().getSqlOnPredicate2();
        on.apply(sqlOnPredicate1, sqlOnPredicate2);
        return queryable2;
    }
}
