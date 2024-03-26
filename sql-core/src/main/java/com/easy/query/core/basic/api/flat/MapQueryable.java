package com.easy.query.core.basic.api.flat;

import com.easy.query.core.basic.api.flat.extension.Filterable;
import com.easy.query.core.basic.api.flat.extension.Groupable;
import com.easy.query.core.basic.api.flat.extension.Havingable;
import com.easy.query.core.basic.api.flat.extension.Joinable;
import com.easy.query.core.basic.api.flat.extension.Orderable;
import com.easy.query.core.basic.api.flat.extension.Selectable;
import com.easy.query.core.basic.api.internal.FilterConfigurable;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableLogicDeletable;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;

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
        Interceptable<MapQueryable>,
        LogicDeletable<MapQueryable>,
        TableReNameable<MapQueryable>,
        TableLogicDeletable<MapQueryable>,
        QueryStrategy<MapQueryable>,
        FilterConfigurable<MapQueryable> {
    ClientQueryable<Map<String, Object>> getClientQueryable();

    @Override
    MapQueryable cloneQueryable();

    @Override
    MapQueryable distinct();
    @Override
    MapQueryable distinct(boolean condition);

    @Override
    MapQueryable limit(long offset, long rows);
    @Override
    MapQueryable limit(boolean condition, long offset, long rows);

    @Override
    MapQueryable asTracking();

    @Override
    MapQueryable asNoTracking();

    @Override
    MapQueryable useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    MapQueryable useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    MapQueryable useConnectionMode(ConnectionModeEnum connectionMode);
}
