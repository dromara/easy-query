package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.proxy.SQLColumnSetExpression;

import java.util.function.Consumer;

/**
 * create time 2023/12/8 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnSetImpl implements SQLColumnSetExpression {
    private final Consumer<Setter> setterConsumer;

    public SQLColumnSetImpl(Consumer<Setter> setterConsumer){
        this.setterConsumer = setterConsumer;
    }

    @Override
    public void accept(Setter s) {
        setterConsumer.accept(s);
    }

    @Override
    public void accept(Selector s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(AsSelector s) {
        throw new UnsupportedOperationException();
    }

}
