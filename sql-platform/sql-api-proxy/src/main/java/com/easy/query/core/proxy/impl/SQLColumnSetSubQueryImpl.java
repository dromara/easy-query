package com.easy.query.core.proxy.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLColumnSetExpression;

/**
 * create time 2023/12/27 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnSetSubQueryImpl implements SQLColumnSetExpression {
    private final TableAvailable table;
    private final String property;
    private final Query<?> subQuery;

    public SQLColumnSetSubQueryImpl(TableAvailable table, String property, Query<?> subQuery){
        this.table = table;
        this.property = property;
        this.subQuery = subQuery;
    }

    @Override
    public void accept(Setter s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(Selector s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(AsSelector s) {
        s.columnSubQueryAs(()->subQuery,property);;
    }
}
