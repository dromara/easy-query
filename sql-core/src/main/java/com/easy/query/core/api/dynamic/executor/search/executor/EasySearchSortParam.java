package com.easy.query.core.api.dynamic.executor.search.executor;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.api.dynamic.executor.search.EasySortType;
import com.easy.query.core.api.dynamic.executor.search.match.EasyTableMatch;
import com.easy.query.core.api.dynamic.executor.search.meta.EasyCondMetaData;


/**
 * 排序参数
 *
 * @author bkbits
 */
public class EasySearchSortParam {
    private final EasyCondMetaData metaData; //类名
    private final String paramName; //参数名
    private final EasyTableMatch tableMatch;
    private final EasySortType sortType; //排序类型

    EasySearchSortParam(
            @NotNull EasyCondMetaData metaData,
            @NotNull String paramName, EasyTableMatch tableMatch,
            @Nullable EasySortType sortType
    ) {
        this.metaData = metaData;
        this.paramName = paramName;
        this.tableMatch = tableMatch;
        this.sortType = sortType;
    }

    public @NotNull EasyCondMetaData getMetaData() {
        return metaData;
    }

    public @NotNull String getParamName() {
        return paramName;
    }

    public @Nullable EasySortType getSortType() {
        return sortType;
    }

    public EasyTableMatch getTableMatch() {
        return tableMatch;
    }
}
