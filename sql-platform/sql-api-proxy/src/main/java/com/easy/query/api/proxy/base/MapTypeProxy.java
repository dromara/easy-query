package com.easy.query.api.proxy.base;

import com.easy.query.api.proxy.key.MapKey;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.impl.SQLAnyColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetPropColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetValueImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapTypeProxy extends AbstractProxyEntity<MapTypeProxy, Map<String,Object>> implements MapTypeAvailable {

    private static final Class<Map<String,Object>> entityClass = EasyObjectUtil.typeCastNullable(Map.class);

    public MapTypeProxy() {
    }

    @Override
    public Class<Map<String,Object>> getEntityClass() {
        return entityClass;
    }


    public <TProperty> MapTypeProxy put(MapKey<TProperty> mapKey, Object val) {
        putPropType(mapKey.getName(),mapKey.getPropType());
        getCurrentEntitySQLContext().accept(new SQLColumnSetValueImpl(null, mapKey.getName(), val));
        return this;
    }
    public <TProperty> MapTypeProxy put(MapKey<TProperty> mapKey, PropTypeColumn<TProperty> val) {
        putPropType(mapKey.getName(),mapKey.getPropType());
        getCurrentEntitySQLContext().accept(new SQLColumnSetPropColumnImpl(null, mapKey.getName(), val));
        return this;
    }
    public <TProperty> SQLAnyColumn<MapTypeProxy,TProperty> get(MapKey<TProperty> mapKey) {
        return new SQLAnyColumnImpl<>(this.getEntitySQLContext(), getTable(), mapKey.getName(), mapKey.getPropType());
    }

    private final Map<String,Class<?>> propTypes=new HashMap<>();
    @Override
    public Map<String,Class<?>> _getResultPropTypes() {
        return propTypes;
    }
    private void putPropType(String colName, Class<?> propType){
        propTypes.put(colName,propType);
    }
//    public SQLAnyColumn<MapProxy,Object> getColumn(String key){
//        return getAnyColumn(key,Object.class);
//    }
//    public <TProperty> SQLAnyColumn<MapProxy,TProperty> getColumn(String key,Class<TProperty> propType){
//        return getAnyColumn(key,propType);
//    }

}