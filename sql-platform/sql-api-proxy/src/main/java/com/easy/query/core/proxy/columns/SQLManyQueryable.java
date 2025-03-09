package com.easy.query.core.proxy.columns;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.internal.ExpressionConfigurable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2024/6/5 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLManyQueryable<TProxy, T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends SQLQueryable<T1Proxy, T1> {

    void _setProxy(TProxy tProxy);

    default SQLManyQueryable<TProxy, T1Proxy, T1> configure(SQLExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> configure) {
        configure.apply(getQueryable());
        return this;
    }

}
