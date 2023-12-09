package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;

import java.util.function.Consumer;

/**
 * create time 2023/12/1 23:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLGroupSelectImpl implements SQLGroupByExpression {
    private final Consumer<GroupSelector> groupConsumer;

    public SQLGroupSelectImpl(Consumer<GroupSelector> groupConsumer) {
        this.groupConsumer = groupConsumer;
    }
    @Override
    public void accept(GroupSelector s) {
        groupConsumer.accept(s);
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
    public EntitySQLContext getEntitySQLContext() {
        throw new UnsupportedOperationException();
    }
}
