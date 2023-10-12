package com.easy.query.api4kt.func.concat;

import com.easy.query.core.func.concat.ColumnConcatSelector;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLKtColumnConcatSelector<T> implements SQLKtColumnConcatSelector<T> {

    private final ColumnConcatSelector columnConcatSelector;

    public DefaultSQLKtColumnConcatSelector(ColumnConcatSelector columnConcatSelector){

        this.columnConcatSelector = columnConcatSelector;
    }

    @Override
    public ColumnConcatSelector getColumnConcatSelector() {
        return columnConcatSelector;
    }
}
