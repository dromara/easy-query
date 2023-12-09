package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;

import java.util.function.Consumer;

/**
 * create time 2023/12/2 17:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectImpl implements SQLSelectExpression {
    private final Consumer<Selector> selectorConsumer;

    public SQLSelectImpl(Consumer<Selector> selectorConsumer){
        this.selectorConsumer = selectorConsumer;
    }
    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public TableAvailable getTable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(Selector s) {
        selectorConsumer.accept(s);
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        throw new UnsupportedOperationException();
    }
}
