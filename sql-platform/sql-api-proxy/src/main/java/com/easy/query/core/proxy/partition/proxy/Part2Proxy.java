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
import com.easy.query.core.proxy.partition.Part2;
import com.easy.query.core.proxy.partition.metadata.Part2EntityMetadata;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Optional;

/**
 * create time 2024/8/4 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Part2Proxy<TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
        TKey2Proxy extends PropTypeColumn<TKey2>, TKey2, TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> extends AbstractPartProxy<Part2Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TSourceProxy, TSource>, Part2<TSource, TKey1, TKey2>, TSourceProxy> {

    private static final Class<Part2> entityClass = Part2.class;


    public Part2Proxy(TSourceProxy table) {
        super(table, 2);
    }

    /**
     * {@link Part2#getPartColumn1()}
     */
    public SQLAnyTypeColumn<Part2Proxy<TKey1Proxy, TKey1,TKey2Proxy, TKey2, TSourceProxy, TSource>, TKey1> partColumn1() {
        return getAnyTypeColumn(Part2.PART_COLUMN1, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[0]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Part2#getPartColumn2()}
     */
    public SQLAnyTypeColumn<Part2Proxy<TKey1Proxy, TKey1,TKey2Proxy, TKey2, TSourceProxy, TSource>, TKey2> partColumn2() {
        return getAnyTypeColumn(Part2.PART_COLUMN2, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[1]).map(o -> o.getPropertyType()).orElse(null)));
    }

    @Override
    public Class<Part2<TSource, TKey1,TKey2>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }

    @Override
    public void accept(AsSelector s) {
        TSourceProxy partTable = entityTable();
        s.columnAll(partTable.getTable());
        SQLSelectAsExpression selectAsExpression = partTable.getEntitySQLContext().getSelectAsExpression();
        if(selectAsExpression!=null){
            selectAsExpression.accept(s);
        }
//        selectTable.
    }

    @Override
    public Part2Proxy<TKey1Proxy, TKey1,TKey2Proxy, TKey2, TSourceProxy, TSource> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        TSourceProxy tSourceProxy = entityTable().create(table, entitySQLContext);
        setEntityTable(tSourceProxy);
        return super.create(table, entitySQLContext);
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        EntityMetadata entityMetadata = entityTable().getEntityMetadata();
        Class<?> key1Class = partColumn1().getPropertyType();
        Class<?> key2Class = partColumn2().getPropertyType();
        JdbcTypeHandlerManager jdbcTypeHandlerManager = entityTable().getEntitySQLContext().getRuntimeContext().getJdbcTypeHandlerManager();
        JdbcTypeHandler jdbcTypeHandler1 = jdbcTypeHandlerManager.getHandler(key1Class);
        JdbcTypeHandler jdbcTypeHandler2 = jdbcTypeHandlerManager.getHandler(key2Class);
        return new Part2EntityMetadata(entityClass, entityMetadata, jdbcTypeHandler1,jdbcTypeHandler2);
    }
}
