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
    /**
     * 当前环境如果是追踪的情况下设置的column值将以原始值original value作为条件,新值作为set进行更新
     * setColumns被调用后id也不会默认添加到条件中去,需要自行添加
     * @param columnNames
     * @return
     */
    MapClientUpdatable<T> whereColumns(String... columnNames);
}
