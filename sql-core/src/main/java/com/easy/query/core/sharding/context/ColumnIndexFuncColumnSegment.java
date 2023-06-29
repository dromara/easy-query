package com.easy.query.core.sharding.context;

import com.easy.query.core.expression.segment.FuncColumnSegment;

/**
 * create time 2023/6/28 22:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnIndexFuncColumnSegment {
    private final int columnIndex;
    private final FuncColumnSegment funcColumnSegment;

    public ColumnIndexFuncColumnSegment(int columnIndex, FuncColumnSegment funcColumnSegment){

        this.columnIndex = columnIndex;
        this.funcColumnSegment = funcColumnSegment;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public FuncColumnSegment getFuncColumnSegment() {
        return funcColumnSegment;
    }
}
