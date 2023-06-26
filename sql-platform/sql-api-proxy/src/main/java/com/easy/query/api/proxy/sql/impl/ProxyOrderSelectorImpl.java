package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.sql.ProxyOrderSelector;
import com.easy.query.core.expression.builder.OrderSelector;

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
}
