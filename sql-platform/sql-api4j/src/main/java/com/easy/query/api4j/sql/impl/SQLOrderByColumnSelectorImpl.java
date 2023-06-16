package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.core.expression.parser.core.base.OrderBySelector;

/**
 * create time 2023/6/16 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLOrderByColumnSelectorImpl<T> implements SQLOrderBySelector<T> {
    private final OrderBySelector<T> orderByColumnSelector;

    public SQLOrderByColumnSelectorImpl(OrderBySelector<T> orderByColumnSelector){

        this.orderByColumnSelector = orderByColumnSelector;
    }
    @Override
    public OrderBySelector<T> getOrderBySelector() {
        return orderByColumnSelector;
    }
}
