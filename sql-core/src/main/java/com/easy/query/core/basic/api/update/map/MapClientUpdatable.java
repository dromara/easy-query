package com.easy.query.core.basic.api.update.map;

import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.update.Updatable;

/**
 * create time 2023/10/3 12:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MapClientUpdatable<T> extends Updatable<T, MapClientUpdatable<T>>,
        SQLExecuteStrategy<MapClientUpdatable<T>> {
    MapClientUpdatable<T> whereColumns(String... columnNames);
}
