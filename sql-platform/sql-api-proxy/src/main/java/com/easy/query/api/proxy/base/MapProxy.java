package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.impl.SQLColumnSetColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetNativeSQLImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetSQLFunctionValueImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetValueImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
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

    @Override
    public Class<Map<String,Object>> getEntityClass() {
        return entityClass;
    }


    public void put(String key, Object val) {
        getEntitySQLContext().accept(new SQLColumnSetValueImpl(null, key, val));
    }

    public <TProperty> void put(String key,SQLColumn<?, TProperty> column) {
        getEntitySQLContext().accept(new SQLColumnSetColumnImpl(null, key, column));
    }
    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<TProperty>,TProperty> void put(String key,TResult val) {
        getEntitySQLContext().accept(new SQLColumnSetSQLFunctionValueImpl(null, key, val));
    }

    public void putSQLNativeSegment(String key,String sqlSegment) {
        putSQLNativeSegment(key,sqlSegment, c -> {
        });
    }

    public void putSQLNativeSegment(String key,String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        getEntitySQLContext().accept(new SQLColumnSetNativeSQLImpl(null, key, sqlSegment, contextConsume));
    }

}