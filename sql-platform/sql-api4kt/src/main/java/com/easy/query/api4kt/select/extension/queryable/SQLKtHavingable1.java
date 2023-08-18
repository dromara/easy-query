package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtHavingable1<T1> {


    default KtQueryable<T1> having(SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    KtQueryable<T1> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression);


}
