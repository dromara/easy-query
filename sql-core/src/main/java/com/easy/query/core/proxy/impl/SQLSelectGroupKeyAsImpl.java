package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2023/12/2 18:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectGroupKeyAsImpl implements SQLSelectAsExpression {
    private final int index;

    public SQLSelectGroupKeyAsImpl(int index) {

        this.index = index;
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

    @Override
    public void accept(AsSelector f) {
        f.groupKeys(index);
    }

    @Override
    public void accept(Selector s) {
        s.groupKeys(index);
    }

    @Override
    public SQLSelectAsExpression alias(TablePropColumn propColumn) {
        return alias(propColumn.getValue());
    }

    @Override
    public SQLSelectAsExpression alias(String propertyAlias) {
        return new SQLSelectAsImpl(f->{
            f.groupKeys(index);
        },f -> {
            f.groupKeysAs(index, propertyAlias);
        },f -> {
            throw new UnsupportedOperationException();
        });
    }
}
