package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Map;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapProxy implements ProxyEntity<MapProxy, Map<String,Object>> {

    public static final MapProxy DEFAULT = new MapProxy();
    private static final Class<Map<String,Object>> entityClass = EasyObjectUtil.typeCastNullable(Map.class);

    private final TableAvailable table;

    private MapProxy() {
        this.table = null;
    }

    public MapProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<Map<String,Object>> getEntityClass() {
        return entityClass;
    }

    @Override
    public MapProxy create(TableAvailable table) {
        return new MapProxy(table);
    }
}