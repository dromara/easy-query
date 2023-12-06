package com.easy.query.core.proxy;

import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;

/**
 * create time 2023/12/1 23:03
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderByExpression {

    default SQLOrderByExpression thenBy(SQLOrderByExpression sqlOrderSelect) {
        return new SQLOrderSelectImpl(x -> {
            accept(x);
            sqlOrderSelect.accept(x);
        });
    }
    default SQLOrderByExpression thenBy(boolean condition,SQLOrderByExpression sqlOrderSelect) {
        if (condition) {
            return new SQLOrderSelectImpl(x -> {
                accept(x);
                sqlOrderSelect.accept(x);
            });
        }
        return SQLOrderByExpression.empty;
    }

    void accept(OrderSelector s);

    SQLOrderByExpression empty = new SQLOrderSelectImpl(s -> {
    });
}
