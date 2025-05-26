package com.easy.query.core.basic.api.flat;

import com.easy.query.core.basic.api.flat.extension.Filterable;
import com.easy.query.core.basic.api.flat.extension.Groupable;
import com.easy.query.core.basic.api.flat.extension.Havingable;
import com.easy.query.core.basic.api.flat.extension.Joinable;
import com.easy.query.core.basic.api.flat.extension.Orderable;
import com.easy.query.core.basic.api.flat.extension.Selectable;
import com.easy.query.core.basic.api.flat.extension.Unionable;
import com.easy.query.core.basic.api.internal.FilterConfigurable;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.TableLogicDeletable;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * create time 2024/3/26 15:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapQueryable extends Query<Map<String, Object>>,
        Joinable,
        Filterable,
        Selectable,
        Groupable,
        Havingable,
        Orderable,
        Unionable,
        Interceptable<MapQueryable>,
        LogicDeletable<MapQueryable>,
        TableReNameable<MapQueryable>,
        TableLogicDeletable<MapQueryable>,
        FilterConfigurable<MapQueryable> {
    ClientQueryable<Map<String, Object>> getClientQueryable();

    @NotNull
    @Override
    MapQueryable cloneQueryable();

    @NotNull
    @Override
    MapQueryable distinct();
    @NotNull
    @Override
    MapQueryable distinct(boolean condition);

    @NotNull
    @Override
    MapQueryable limit(long offset, long rows);
    @NotNull
    @Override
    MapQueryable limit(boolean condition, long offset, long rows);

    @NotNull
    @Override
    MapQueryable asTracking();

    @NotNull
    @Override
    MapQueryable asNoTracking();

    @NotNull
    @Override
    MapQueryable useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @NotNull
    @Override
    MapQueryable useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @NotNull
    @Override
    MapQueryable useConnectionMode(ConnectionModeEnum connectionMode);
}
