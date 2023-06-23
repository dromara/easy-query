package com.easy.query.api4j.sql.impl;

import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/6/16 22:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLOrderByColumnSelectorImpl<T> implements SQLOrderBySelector<T> {
    private final ColumnOrderSelector<T> orderByColumnSelector;

    public SQLOrderByColumnSelectorImpl(ColumnOrderSelector<T> orderByColumnSelector){

        this.orderByColumnSelector = orderByColumnSelector;
    }
    @Override
    public ColumnOrderSelector<T> getOrderBySelector() {
        return orderByColumnSelector;
    }
}
