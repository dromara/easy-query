package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Map;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapProxy extends AbstractProxyEntity<MapProxy, Map<String,Object>> {

    private static final Class<Map<String,Object>> entityClass = EasyObjectUtil.typeCastNullable(Map.class);

    public static MapProxy createTable() {
        return new MapProxy();
    }

    private MapProxy() {
    }

    @Override
    public Class<Map<String,Object>> getEntityClass() {
        return entityClass;
    }
}