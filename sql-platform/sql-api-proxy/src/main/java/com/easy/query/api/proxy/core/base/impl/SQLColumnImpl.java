package com.easy.query.api.proxy.core.base.impl;

import com.easy.query.api.proxy.core.base.SQLColumn;

/**
 * create time 2023/6/22 13:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnImpl implements SQLColumn {
    private final String property;

    public SQLColumnImpl(String property){

        this.property = property;
    }
    @Override
    public String value() {
        return property;
    }
}
