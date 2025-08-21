package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2025/8/21 10:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityThisConfigurable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    EntityQueryable<T1Proxy, T1> thisConfigure(SQLFuncExpression1<EntityQueryable<T1Proxy, T1>, EntityQueryable<T1Proxy, T1>> thisConfigureExpression);
}
