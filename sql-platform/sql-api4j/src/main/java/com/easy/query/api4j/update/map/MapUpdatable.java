package com.easy.query.api4j.update.map;

import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.basic.api.update.map.MapClientUpdatable;

/**
 * create time 2023/10/3 18:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapUpdatable<T> extends Updatable<T, MapUpdatable<T>>, SQLExecuteStrategy<MapUpdatable<T>> {
    MapClientUpdatable<T> getMapClientUpdate();
    MapUpdatable<T> whereColumns(String... columnNames);
}
