package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable1<T1> {

    default ClientQueryable<T1> groupBy(SQLActionExpression1<ColumnGroupSelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable<T1> groupBy(boolean condition, SQLActionExpression1<ColumnGroupSelector<T1>> selectExpression);
}
