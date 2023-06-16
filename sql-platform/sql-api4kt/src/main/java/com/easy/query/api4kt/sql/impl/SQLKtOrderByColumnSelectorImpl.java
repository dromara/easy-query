package com.easy.query.api4kt.sql.impl;

import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.core.expression.parser.core.base.OrderBySelector;

/**
 * create time 2023/6/16 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLKtOrderByColumnSelectorImpl<T> implements SQLKtOrderBySelector<T> {
    private final OrderBySelector<T> orderByColumnSelector;

    public SQLKtOrderByColumnSelectorImpl(OrderBySelector<T> orderByColumnSelector){

        this.orderByColumnSelector = orderByColumnSelector;
    }
    @Override
    public OrderBySelector<T> getOrderBySelector() {
        return orderByColumnSelector;
    }
}
