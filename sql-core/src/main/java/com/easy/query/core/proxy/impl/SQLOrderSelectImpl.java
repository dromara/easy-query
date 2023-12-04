package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.proxy.SQLOrderSelect;

import java.util.function.Consumer;

/**
 * create time 2023/12/1 23:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLOrderSelectImpl implements SQLOrderSelect {
    private final Consumer<OrderSelector> orderConsumer;

    public SQLOrderSelectImpl(Consumer<OrderSelector> orderConsumer) {
        this.orderConsumer = orderConsumer;
    }


    @Override
    public void accept(OrderSelector s) {
        orderConsumer.accept(s);
    }

}
