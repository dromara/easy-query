package com.easy.query.api.proxy.func.column;

import com.easy.query.core.func.column.ColumnFuncSelector;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyColumnFuncSelectorImpl implements ProxyColumnFuncSelector {

    private final ColumnFuncSelector columnConcatSelector;

    public ProxyColumnFuncSelectorImpl(ColumnFuncSelector columnConcatSelector){

        this.columnConcatSelector = columnConcatSelector;
    }

    @Override
    public ColumnFuncSelector getColumnConcatSelector() {
        return columnConcatSelector;
    }
}
