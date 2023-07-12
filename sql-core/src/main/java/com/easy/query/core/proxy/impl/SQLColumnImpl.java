package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/22 13:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnImpl<TProperty> implements SQLColumn<TProperty> {
    private final TableAvailable table;
    private final String property;

    public SQLColumnImpl(TableAvailable table, String property){
        this.table = table;

        this.property = property;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String value() {
        return property;
    }
}
