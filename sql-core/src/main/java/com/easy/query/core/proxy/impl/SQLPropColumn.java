package com.easy.query.core.proxy.impl;

import com.easy.query.core.proxy.PropColumn;

/**
 * create time 2023/9/17 16:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLPropColumn implements PropColumn {
    private final String prop;

    public SQLPropColumn(String prop){

        this.prop = prop;
    }
    @Override
    public String getValue() {
        return prop;
    }
}
