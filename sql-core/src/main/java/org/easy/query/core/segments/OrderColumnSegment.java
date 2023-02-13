package org.easy.query.core.segments;

import org.easy.query.core.impl.SelectContext;

/**
 * @FileName: OrderColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 22:18
 * @Created by xuejiaming
 */
public class OrderColumnSegment extends ColumnSegment{
    public boolean isAsc() {
        return asc;
    }

    private final boolean asc;

    public OrderColumnSegment(int index, String columnName, SelectContext selectContext, boolean asc) {
        super(index, columnName, selectContext);
        this.asc = asc;
    }

}
