package com.easy.query.api.proxy.core.base.impl;

import com.easy.query.api.proxy.core.SQLSelectColumn;

/**
 * create time 2023/6/22 12:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLSelectColumnImpl<TSQLSelect> implements SQLSelectColumn<TSQLSelect> {
    private final TSQLSelect sqlSelect;
    private final String property;

    public SQLSelectColumnImpl(TSQLSelect sqlSelect,String property){

        this.sqlSelect = sqlSelect;
        this.property = property;
    }
    @Override
    public String value() {
        return property;
    }

    @Override
    public TSQLSelect and() {
        return sqlSelect;
    }
}
