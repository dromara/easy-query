package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLSelectAs;
import com.easy.query.core.proxy.TablePropColumn;

import java.util.function.Consumer;

/**
 * create time 2023/12/1 23:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectAsImpl implements SQLSelectAs {

    private final Consumer<AsSelector> asSelectConsumer;

    public SQLSelectAsImpl(Consumer<AsSelector> asSelectConsumer){
        this.asSelectConsumer = asSelectConsumer;
    }

    @Override
    public void accept(AsSelector f) {
        asSelectConsumer.accept(f);
    }

    @Override
    public String value() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLSelectAs as(TablePropColumn propColumn) {
        return new SQLSelectAsImpl(s -> {
            s.columnAs(this.getTable(), this.value(), propColumn.value());
        });
    }


    @Override
    public TableAvailable getTable() {
        throw new UnsupportedOperationException();
    }
}
