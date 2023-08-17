package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable1<T1> {

    default ClientQueryable<T1> groupBy(SQLExpression1<ColumnGroupSelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable<T1> groupBy(boolean condition, SQLExpression1<ColumnGroupSelector<T1>> selectExpression);
}
