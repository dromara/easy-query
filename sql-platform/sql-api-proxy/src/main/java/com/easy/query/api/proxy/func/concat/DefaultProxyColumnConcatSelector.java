package com.easy.query.api.proxy.func.concat;

import com.easy.query.core.func.concat.ColumnConcatSelector;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultProxyColumnConcatSelector implements ProxyColumnConcatSelector {

    private final ColumnConcatSelector columnConcatSelector;

    public DefaultProxyColumnConcatSelector(ColumnConcatSelector columnConcatSelector){

        this.columnConcatSelector = columnConcatSelector;
    }

    @Override
    public ColumnConcatSelector getColumnConcatSelector() {
        return columnConcatSelector;
    }
}
