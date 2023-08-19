package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroupable1<T1> {

    default Queryable<T1> groupBy(SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    Queryable<T1> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression);
}
