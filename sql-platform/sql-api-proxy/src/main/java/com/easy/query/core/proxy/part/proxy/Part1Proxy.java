package com.easy.query.core.proxy.part.proxy;

import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.part.Part1;
import com.easy.query.core.proxy.part.metadata.PartEntityMetadata;
import com.easy.query.core.proxy.part.metadata.PartColumn;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * create time 2024/8/4 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Part1Proxy<TKey1Proxy extends PropTypeColumn<TKey1>, TKey1, TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> extends AbstractPartProxy<Part1Proxy<TKey1Proxy, TKey1, TSourceProxy, TSource>, Part1<TSource, TKey1>, TSourceProxy> {

    private static final Class<Part1> entityClass = Part1.class;


    public Part1Proxy(TSourceProxy table) {
        super(table, 1);
    }

    /**
     * {@link Part1#getPartColumn1()}
     */
    public SQLAnyTypeColumn<Part1Proxy<TKey1Proxy, TKey1, TSourceProxy, TSource>, TKey1> partColumn1() {
        return getAnyTypeColumn(Part1.PART_COLUMN1, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[0]).map(o -> o.getPropertyType()).orElse(null)));
    }

    @Override
    public Class<Part1<TSource, TKey1>> getEntityClass() {
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
    public Part1Proxy<TKey1Proxy, TKey1, TSourceProxy, TSource> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        TSourceProxy tSourceProxy = entityTable().create(table, entitySQLContext);
        setEntityTable(tSourceProxy);
        return super.create(table, entitySQLContext);
    }

    @Override
    public EntityMetadata getEntityMetadata() {
        EntityMetadata entityMetadata = entityTable().getEntityMetadata();
        Class<?> keyClass = partColumn1().getPropertyType();
        JdbcTypeHandler jdbcTypeHandler = entityTable().getEntitySQLContext().getRuntimeContext().getJdbcTypeHandlerManager().getHandler(keyClass);
        Map<String, PartColumn> partColumnMap = new HashMap<>();
        partColumnMap.put(Part1.PART_COLUMN1, new PartColumn(jdbcTypeHandler, obj -> {
            return ((Part1<?, ?>) obj).getPartColumn1();
        }, (obj, value) -> {
            ((Part1<?, ?>) obj).setPartColumn1(EasyObjectUtil.typeCastNullable(value));
        }));
        return new PartEntityMetadata(entityClass, entityMetadata, () -> {
            Part1<Object, Object> r = new Part1<>();
            Object entity = entityMetadata.getBeanConstructorCreator().get();
            r.setEntity(entity);
            return r;
        }, partColumnMap);
    }
}
