package com.easy.query.core.api.dynamic.query;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/9/26 09:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class WhereObjectEntry {
    private final TableAvailable table;
    private final String property;

    public WhereObjectEntry(TableAvailable table, String property){

        this.table = table;
        this.property = property;
    }

    public TableAvailable getTable() {
        return table;
    }

    public String getProperty() {
        return property;
    }
}
