package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupSelect;

/**
 * create time 2023/12/4 10:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityGroupable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {

    default EntityQueryable<T1Proxy, T1> groupBy(SQLFuncExpression1<T1Proxy, SQLGroupSelect> selectExpression) {
        return groupBy(true, selectExpression);
    }

    EntityQueryable<T1Proxy, T1> groupBy(boolean condition, SQLFuncExpression1<T1Proxy, SQLGroupSelect> selectExpression);
}
