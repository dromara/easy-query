package com.easy.query.core.util.common;

import com.easy.query.core.metadata.TreeDeepItem;

import java.util.List;

/**
 * create time 2025/8/27 12:52
 * 文件说明
 *
 * @author xuejiaming
 */

public class ListTreeDeepItemAvailable implements TreeDeepItemAvailable{

    private final List<TreeDeepItem> deepItems;

    public ListTreeDeepItemAvailable(List<TreeDeepItem> deepItems){
        this.deepItems = deepItems;
    }

    @Override
    public long getDeep(Object node, int index) {
        return deepItems.get(index).deep;
    }
}