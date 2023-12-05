package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;

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
    public void accept(AsSelector f) {
        f.groupKeys(index);
    }

    @Override
    public void accept(Selector f) {
        f.groupKeys(index);
    }

    @Override
    public SQLSelectAsExpression as(TablePropColumn propColumn) {
        return new SQLSelectAsImpl(f->{
            f.groupKeys(index);
        },f -> {
            f.groupKeysAs(index, propColumn.getValue());
        });
    }
}
