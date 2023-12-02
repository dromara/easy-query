package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLSelectAs;
import com.easy.query.core.proxy.TablePropColumn;

/**
 * create time 2023/12/2 18:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectGroupKeyAsImpl implements SQLSelectAs {
    private final int index;

    public SQLSelectGroupKeyAsImpl(int index) {

        this.index = index;
    }

    @Override
    public String value() {
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
    public SQLSelectAs as(TablePropColumn propColumn) {
        return new SQLSelectAsImpl(f -> {
            f.groupKeysAs(index, propColumn.value());
        });
    }
}
