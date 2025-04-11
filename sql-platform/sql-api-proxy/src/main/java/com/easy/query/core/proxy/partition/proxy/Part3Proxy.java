package com.easy.query.core.proxy.partition.proxy;

import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.partition.Part3;
import com.easy.query.core.proxy.partition.metadata.Part3EntityMetadata;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Optional;

/**
 * create time 2024/8/4 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Part3Proxy<TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
        TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
        TKey3Proxy extends PropTypeColumn<TKey3>, TKey3, TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> extends AbstractPartProxy<Part3Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TSourceProxy, TSource>, Part3<TSource, TKey1, TKey2, TKey3>, TSourceProxy> {

    private static final Class<Part3> entityClass = Part3.class;


    public Part3Proxy(TSourceProxy table) {
        super(table, 3);
    }

    /**
     * {@link Part3#getPartColumn1()}
     */
    public SQLAnyTypeColumn<Part3Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TSourceProxy, TSource>, TKey1> partColumn1() {
        return getAnyTypeColumn(Part3.PART_COLUMN1, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[0]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Part3#getPartColumn2()}
     */
    public SQLAnyTypeColumn<Part3Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TSourceProxy, TSource>, TKey2> partColumn2() {
        return getAnyTypeColumn(Part3.PART_COLUMN2, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[1]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Part3#getPartColumn3()}
     */
    public SQLAnyTypeColumn<Part3Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TSourceProxy, TSource>, TKey2> partColumn3() {
        return getAnyTypeColumn(Part3.PART_COLUMN2, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[2]).map(o -> o.getPropertyType()).orElse(null)));
    }

    @Override
    public Class<Part3<TSource, TKey1, TKey2, TKey3>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }

    @Override
    public void accept(AsSelector s) {
        TSourceProxy partTable = entityTable();
        s.columnAll(partTable.getTable());
        SQLSelectAsExpression selectAsExpression = partTable.getEntitySQLContext().getSelectAsExpression();
        if (selectAsExpression != null) {
            selectAsExpression.accept(s);
        }
//        selectTable.
    }

    @Override
    public Part3Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TSourceProxy, TSource> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        TSourceProxy tSourceProxy = entityTable().create(table, entitySQLContext);
        setEntityTable(tSourceProxy);
        return super.create(table, entitySQLContext);
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        EntityMetadata entityMetadata = entityTable().getEntityMetadata();
        Class<?> key1Class = partColumn1().getPropertyType();
        Class<?> key2Class = partColumn2().getPropertyType();
        Class<?> key3Class = partColumn3().getPropertyType();
        JdbcTypeHandlerManager jdbcTypeHandlerManager = entityTable().getEntitySQLContext().getRuntimeContext().getJdbcTypeHandlerManager();
        JdbcTypeHandler jdbcTypeHandler1 = jdbcTypeHandlerManager.getHandler(key1Class);
        JdbcTypeHandler jdbcTypeHandler2 = jdbcTypeHandlerManager.getHandler(key2Class);
        JdbcTypeHandler jdbcTypeHandler3 = jdbcTypeHandlerManager.getHandler(key3Class);
        return new Part3EntityMetadata(entityClass, entityMetadata, jdbcTypeHandler1, jdbcTypeHandler2, jdbcTypeHandler3);
    }
}
