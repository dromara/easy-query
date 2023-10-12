package com.easy.query.api4j.func.concat;

import com.easy.query.core.func.concat.ColumnConcatSelector;

/**
 * create time 2023/10/11 22:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSQLColumnConcatSelector<T> implements SQLColumnConcatSelector<T> {

    private final ColumnConcatSelector columnConcatSelector;

    public DefaultSQLColumnConcatSelector(ColumnConcatSelector columnConcatSelector){

        this.columnConcatSelector = columnConcatSelector;
    }

    @Override
    public ColumnConcatSelector getColumnConcatSelector() {
        return columnConcatSelector;
    }
}
