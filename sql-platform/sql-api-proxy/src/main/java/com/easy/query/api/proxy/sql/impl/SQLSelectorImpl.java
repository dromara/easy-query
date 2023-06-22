package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.api.proxy.sql.SQLColumnPropertyFunction;
import com.easy.query.api.proxy.sql.SQLSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/22 22:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectorImpl implements SQLSelector {
    private final Selector selector;

    public SQLSelectorImpl(Selector selector){

        this.selector = selector;
    }
    @Override
    public Selector getSelector() {
        return selector;
    }

    @Override
    public SQLSelector columns(SQLColumn<?>... column) {
        return null;
    }

    @Override
    public SQLSelector column(SQLColumn<?> column) {
        return null;
    }

    @Override
    public SQLSelector columnFunc(SQLColumnPropertyFunction sqlColumnPropertyFunction) {
        return null;
    }

    @Override
    public SQLSelector columnIgnore(SQLColumn<?> column) {
        return null;
    }

    @Override
    public SQLSelector columnAll(TableAvailable table) {
        return null;
    }
}
