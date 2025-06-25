package com.easy.query.core.api.dynamic.executor.search.executor;

import com.easy.query.core.api.dynamic.executor.search.EasySortType;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * 排序项
 *
 * @author bkbits
 */
class EasySearchSortItem {
    private final TableAvailable table;
    private final String name;
    private final EasySortType sortType;
    private final int sortOrder;

    public EasySearchSortItem(
            TableAvailable table,
            String name,
            EasySortType sortType,
            int sortOrder
    ) {
        this.table = table;
        this.name = name;
        this.sortType = sortType;
        this.sortOrder = sortOrder;
    }

    public TableAvailable getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public EasySortType getSortType() {
        return sortType;
    }

    public int getSortOrder() {
        return sortOrder;
    }
}
