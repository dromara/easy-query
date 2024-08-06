package com.easy.query.core.proxy.partition.proxy;

import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.partition.Partition2;
import com.easy.query.core.proxy.partition.metadata.Partition1EntityMetadata;
import com.easy.query.core.proxy.partition.metadata.Partition2EntityMetadata;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Optional;

/**
 * create time 2024/8/4 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Partition2Proxy<TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
        TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> extends AbstractPartitionProxy<Partition2Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TSourceProxy, TSource>, Partition2<TSource, TKey1, TKey2>, TSourceProxy> {

    private static final Class<Partition2> entityClass = Partition2.class;


    public Partition2Proxy(TSourceProxy table) {
        super(table, 2);
    }

    /**
     * {@link Partition2#getPartitionColumn1()}
     */
    public SQLAnyTypeColumn<Partition2Proxy<TKey1Proxy, TKey1,TKey2Proxy, TKey2, TSourceProxy, TSource>, TKey1> partitionColumn1() {
        return getAnyTypeColumn(Partition2.PARTITION_COLUMN1, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartitionByPropTypes()[0]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Partition2#getPartitionColumn2()}
     */
    public SQLAnyTypeColumn<Partition2Proxy<TKey1Proxy, TKey1,TKey2Proxy, TKey2, TSourceProxy, TSource>, TKey2> partitionColumn2() {
        return getAnyTypeColumn(Partition2.PARTITION_COLUMN2, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartitionByPropTypes()[1]).map(o -> o.getPropertyType()).orElse(null)));
    }

    @Override
    public Class<Partition2<TSource, TKey1,TKey2>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }

    @Override
    public void accept(AsSelector s) {
        TSourceProxy partitionTable = entityTable();
        s.columnAll(partitionTable.getTable());
//        selectTable.
    }

    @Override
    public Partition2Proxy<TKey1Proxy, TKey1,TKey2Proxy, TKey2, TSourceProxy, TSource> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        TSourceProxy tSourceProxy = entityTable().create(table, entitySQLContext);
        setEntityTable(tSourceProxy);
        return super.create(table, entitySQLContext);
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        EntityMetadata entityMetadata = entityTable().getEntityMetadata();
        Class<?> key1Class = partitionColumn1().getPropertyType();
        Class<?> key2Class = partitionColumn2().getPropertyType();;
        JdbcTypeHandlerManager jdbcTypeHandlerManager = entityTable().getEntitySQLContext().getRuntimeContext().getJdbcTypeHandlerManager();
        JdbcTypeHandler jdbcTypeHandler1 = jdbcTypeHandlerManager.getHandler(key1Class);
        JdbcTypeHandler jdbcTypeHandler2 = jdbcTypeHandlerManager.getHandler(key2Class);
        return new Partition2EntityMetadata(entityClass, entityMetadata, jdbcTypeHandler1,jdbcTypeHandler2);
    }
}
