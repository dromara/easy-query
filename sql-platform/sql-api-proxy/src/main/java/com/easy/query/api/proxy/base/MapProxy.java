package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.impl.SQLColumnSetPropColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetValueImpl;
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

    public MapProxy() {
    }

    /**
     * 创建匿名对象
     * @param anonymousName 匿名对象名称
     * @param packageWith 和哪个字节在同一个包
     */
    public MapProxy(String anonymousName,Class<?> packageWith) {
    }
    /**
     * 创建匿名对象
     * @param anonymousName 匿名对象名称
     * @param packageWith 和哪个字节在同一个包
     * @param entityFileProxy 是否使用{@link com.easy.query.core.annotation.EntityFileProxy}默认不使用
     */
    public MapProxy(String anonymousName,Class<?> packageWith,boolean entityFileProxy) {
    }
    public MapProxy(String anonymousName,String moduleName,String packageName) {
    }
    public MapProxy(String anonymousName,String moduleName,String packageName,boolean entityFileProxy) {
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