package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/23 23:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyOrderSelectorImpl implements ProxyOrderSelector {
    private final OrderSelector orderSelector;

    public ProxyOrderSelectorImpl(OrderSelector orderSelector){

        this.orderSelector = orderSelector;
    }
    @Override
    public OrderSelector getOrderSelector() {
        return orderSelector;
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(orderSelector);
    }

    @Override
    public ProxyOrderSelector castChain() {
        return this;
    }
}
