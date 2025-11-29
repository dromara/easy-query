package com.easy.query.api.proxy.base;

import com.easy.query.api.proxy.enums.MapKeyModeEnum;
import com.easy.query.api.proxy.enums.ValueTypeMode;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.columns.types.impl.SQLAnyTypeColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetPropColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetValueImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * create time 2023/6/29 09:22
 *
 * @author xuejiaming
 */
public class MapProxy extends AbstractProxyEntity<MapProxy, Map<String, Object>> implements MapTypeAvailable {

    private static final Class<Map<String, Object>> entityClass = EasyObjectUtil.typeCastNullable(Map.class);
    private ValueTypeMode valueTypeMode;


    public MapProxy() {
        this(ValueTypeMode.OBJECT);
    }

    public MapProxy(ValueTypeMode valueTypeMode) {
        this.valueTypeMode = valueTypeMode;
    }

    @Override
    public Class<Map<String, Object>> getEntityClass() {
        return entityClass;
    }

    private Class<?> getValueType(Object val) {
        if (val != null) {
            return val.getClass();
        }
        return Object.class;
    }

    public void put(String key, Object val) {
        putPropType(key, getValueType(val));
        getCurrentEntitySQLContext().accept(new SQLColumnSetValueImpl(null, key, val));
    }

    public <TProperty> MapProxy put(String key, PropTypeColumn<TProperty> val) {
        putPropType(key, val.getPropertyType());
        getCurrentEntitySQLContext().accept(new SQLColumnSetPropColumnImpl(null, key, val));
        return this;
    }

    public <TProxy, TProperty> MapProxy put(SQLColumn<TProxy, TProperty> column) {
        putPropType(column.getValue(), column.getPropertyType());
        getCurrentEntitySQLContext().accept(new SQLColumnSetPropColumnImpl(column.getTable(), column.getValue(), column));
        return this;
    }

    public SQLAnyTypeColumn<MapProxy, Object> get(String key) {
        return getAnyTypeColumn(key, Object.class);
    }

    public <TProperty> SQLAnyTypeColumn<MapProxy, TProperty> get(String key, Class<TProperty> propType) {
        return getAnyTypeColumn(key, propType);
    }

    public <TRProxy extends ProxyEntity<TRProxy, TREntity>, TREntity> MapProxy selectAll(TRProxy proxy, MapKeyModeEnum mapKeyMode) {
        if (MapKeyModeEnum.COLUMN_NAME == mapKeyMode) {
            MapProxy mapProxy = super.selectAll(proxy);
            putAllPropType(proxy.getTable());
            return mapProxy;
        } else {

            EntityMetadata entityMetadata = proxy.getTable().getEntityMetadata();
            for (Map.Entry<String, ColumnMetadata> stringColumnMetadataEntry : entityMetadata.getProperty2ColumnMap().entrySet()) {
                String propertyName = stringColumnMetadataEntry.getKey();
                ColumnMetadata value = stringColumnMetadataEntry.getValue();
                SQLAnyTypeColumnImpl<Object, ?> column = new SQLAnyTypeColumnImpl<>(entitySQLContext, proxy.getTable(), propertyName, value.getPropertyType());
                put(propertyName, column);
            }
            return castChain();
        }
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TREntity>, TREntity> MapProxy selectAll(TRProxy proxy) {
        MapProxy mapProxy = super.selectAll(proxy);
        putAllPropType(proxy.getTable());
        return mapProxy;
    }

    private void putAllPropType(TableAvailable table) {
        if (valueTypeMode == ValueTypeMode.TRY_TYPE) {
            for (Map.Entry<String, ColumnMetadata> kv : table.getEntityMetadata().getProperty2ColumnMap().entrySet()) {
                ColumnMetadata value = kv.getValue();
                putPropType(value.getName(), value.getPropertyType());
            }
        }
    }

    private final Map<String, Class<?>> propTypes = new HashMap<>();

    @Override
    public Map<String, Class<?>> _getResultPropTypes() {
        return propTypes;
    }

    private void putPropType(String colName, Class<?> propType) {
        if (valueTypeMode == ValueTypeMode.TRY_TYPE) {
            propTypes.put(colName, propType);
        }
    }
}