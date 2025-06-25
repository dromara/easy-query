package com.easy.query.core.api.dynamic.executor.search.executor;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.api.dynamic.executor.search.match.EasyTableMatch;
import com.easy.query.core.api.dynamic.executor.search.meta.EasyCondMetaData;

/**
 * 参数名信息
 * <p>包含分组名，搜索元数据，表匹配器，参数名，参数索引</p>
 *
 * @author bkbits
 */
public class EasySearchParamName {
    private final String groupName; //分组名
    private final EasyCondMetaData metaData; //搜索元数据
    private final EasyTableMatch tableMatch;
    private final String paramName; //参数名
    private final int index; //索引

    public EasySearchParamName(
            @Nullable String groupName,
            @NotNull EasyCondMetaData metaData,
            @NotNull EasyTableMatch tableMatch,
            @NotNull String paramName,
            @Nullable Integer index
    ) {
        this.groupName = groupName;
        this.metaData = metaData;
        this.paramName = paramName;
        this.index = index != null ? index : 0;
        this.tableMatch = tableMatch;
    }

    public @Nullable String getGroupName() {
        return groupName;
    }

    public @NotNull EasyCondMetaData getMetaData() {
        return metaData;
    }

    public @NotNull String getParamName() {
        return paramName;
    }

    public @Nullable int getIndex() {
        return index;
    }

    public @NotNull EasyTableMatch getTableMatch() {
        return tableMatch;
    }
}
