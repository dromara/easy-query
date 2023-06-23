package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/23 14:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class OrderSelectorImpl implements OrderSelector {
    @Override
    public OrderSelector column(TableAvailable table, String property) {
        return null;
    }

    @Override
    public OrderSelector columnFunc(TableAvailable table, ColumnPropertyFunction columnPropertyFunction) {
        return null;
    }

    @Override
    public OrderSelector columnConst(TableAvailable table, String columnConst) {
        return null;
    }

    @Override
    public OrderSelector columnIgnore(TableAvailable table, String property) {
        return null;
    }

    @Override
    public OrderSelector columnAll(TableAvailable table) {
        return null;
    }
}
