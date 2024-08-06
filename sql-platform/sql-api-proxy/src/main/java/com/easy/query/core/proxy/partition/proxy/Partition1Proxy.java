package com.easy.query.core.proxy.partition.proxy;

import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.partition.Partition1;
import com.easy.query.core.proxy.partition.metadata.PartitionBy1EntityMetadata;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Optional;

/**
 * create time 2024/8/4 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Partition1Proxy<TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> extends AbstractPartitionProxy<Partition1Proxy<TKey1Proxy, TKey1, TSourceProxy, TSource>, Partition1<TSource, TKey1>, TSourceProxy> {

    private static final Class<Partition1> entityClass = Partition1.class;


    public Partition1Proxy(TSourceProxy table) {
        super(table,1);
    }
    /**
     * {@link Partition1#getPartitionColumn1()}
     */
    public SQLAnyTypeColumn<Partition1Proxy<TKey1Proxy, TKey1, TSourceProxy, TSource>, TKey1> partitionColumn1() {
        return getAnyTypeColumn(Partition1.PARTITION_COLUMN1,EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartitionByPropTypes()[0]).map(o -> o.getPropertyType()).orElse(null)));
    }

    @Override
    public Class<Partition1<TSource, TKey1>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }

    @Override
    public void accept(AsSelector s) {
        TSourceProxy partitionTable = entityTable();
        s.columnAll(partitionTable.getTable());
//        selectTable.
    }

    @Override
    public Partition1Proxy<TKey1Proxy, TKey1, TSourceProxy, TSource> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        TSourceProxy tSourceProxy = entityTable().create(table, entitySQLContext);
        setEntityTable(tSourceProxy);
        return super.create(table, entitySQLContext);
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        EntityMetadata entityMetadata = entityTable().getEntityMetadata();
        Class<?> keyClass = Optional.ofNullable(getPartitionByPropTypes()[0]).map(o -> o.getPropertyType()).orElse(null);
        JdbcTypeHandler jdbcTypeHandler = entityTable().getEntitySQLContext().getRuntimeContext().getJdbcTypeHandlerManager().getHandler(keyClass);
        return new PartitionBy1EntityMetadata(entityClass,entityMetadata,jdbcTypeHandler);
    }
}
