package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.core.SQLNativeAble;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;

import java.util.function.Consumer;

/**
 * create time 2023/12/1 23:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectNativeAsImpl implements SQLSelectAsExpression {

    private final Consumer<SQLNativeAble> selectorConsumer;

    public SQLSelectNativeAsImpl(Consumer<SQLNativeAble> selectorConsumer) {
        this.selectorConsumer=selectorConsumer;
    }

    @Override
    public void accept(GroupSelector s) {
        selectorConsumer.accept(s);
    }

    @Override
    public void accept(AsSelector s) {
        selectorConsumer.accept(s);
    }


    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }


    @Override
    public SQLSelectAsExpression as(TablePropColumn propColumn) {
        return as(propColumn.getValue());
    }

    @Override
    public SQLSelectAsExpression as(String propertyAlias) {
        return new SQLSelectAsImpl(s -> {
            s.columnAs(this.getTable(), this.getValue(), propertyAlias);
        }, s -> {
            s.columnAs(this.getTable(), this.getValue(), propertyAlias);
        }, s -> {
            throw new UnsupportedOperationException();
        });
    }

    @Override
    public TableAvailable getTable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        throw new UnsupportedOperationException();
    }
}
