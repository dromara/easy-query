package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;

/**
 * create time 2023/12/1 23:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderSelect {

    void accept(OrderSelector s);

    SQLOrderSelect empty = new SQLOrderSelectImpl(s -> {
    });
}
