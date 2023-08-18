package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtGroupable1<T1> {

    default KtQueryable<T1> groupBy(SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    KtQueryable<T1> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression);
}
