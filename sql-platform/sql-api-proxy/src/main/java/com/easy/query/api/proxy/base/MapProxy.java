package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.impl.SQLColumnSetPropColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetValueImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Map;

/**
 * create time 2023/6/29 09:22
 * 建议使用{@link MapTypeProxy}
 *
 * @author xuejiaming
 */
@Deprecated
public class MapProxy extends AbstractProxyEntity<MapProxy, Map<String,Object>>{

    private static final Class<Map<String,Object>> entityClass = EasyObjectUtil.typeCastNullable(Map.class);

    public static MapProxy createTable() {
        return new MapProxy();
    }

    public MapProxy() {
    }

    @Override
    public Class<Map<String,Object>> getEntityClass() {
        return entityClass;
    }


    public void put(String key, Object val) {
        getEntitySQLContext().accept(new SQLColumnSetValueImpl(null, key, val));
    }

    public <TProperty> MapProxy put(String key,PropTypeColumn<TProperty> val) {
        getEntitySQLContext().accept(new SQLColumnSetPropColumnImpl(null, key, val));
        return this;
    }
    public <TProperty> MapProxy put(PropTypeColumn<TProperty> val) {
        getEntitySQLContext().accept(new SQLColumnSetPropColumnImpl(null, val.getValue(), val));
        return this;
    }

    //    public SQLAnyColumn<MapProxy,Object> getColumn(String key){
//        return getAnyColumn(key,Object.class);
//    }
//    public <TProperty> SQLAnyColumn<MapProxy,TProperty> getColumn(String key,Class<TProperty> propType){
//        return getAnyColumn(key,propType);
//    }

}