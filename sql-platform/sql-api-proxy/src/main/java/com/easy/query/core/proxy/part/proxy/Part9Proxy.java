package com.easy.query.core.proxy.part.proxy;

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
import com.easy.query.core.proxy.part.Part9;
import com.easy.query.core.proxy.part.metadata.PartColumn;
import com.easy.query.core.proxy.part.metadata.PartEntityMetadata;
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
public class Part9Proxy<TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
        TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
        TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
        TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
        TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
        TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
        TKey7Proxy extends PropTypeColumn<TKey7>, TKey7,
        TKey8Proxy extends PropTypeColumn<TKey8>, TKey8,
        TKey9Proxy extends PropTypeColumn<TKey9>, TKey9,
        TSourceProxy extends ProxyEntity<TSourceProxy, TSource>, TSource> extends AbstractPartProxy<Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TKey9Proxy, TKey9, TSourceProxy, TSource>, Part9<TSource, TKey1, TKey2, TKey3, TKey4, TKey5, TKey6, TKey7, TKey8, TKey9>, TSourceProxy> {

    private static final Class<Part9> entityClass = Part9.class;


    public Part9Proxy(TSourceProxy table) {
        super(table, 9);
    }

    /**
     * {@link Part9#getPartColumn1()}
     */
    public SQLAnyTypeColumn<Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8,TKey9Proxy, TKey9, TSourceProxy, TSource>, TKey1> partColumn1() {
        return getAnyTypeColumn(Part9.PART_COLUMN1, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[0]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Part9#getPartColumn2()}
     */
    public SQLAnyTypeColumn<Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8,TKey9Proxy, TKey9, TSourceProxy, TSource>, TKey2> partColumn2() {
        return getAnyTypeColumn(Part9.PART_COLUMN2, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[1]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Part9#getPartColumn3()}
     */
    public SQLAnyTypeColumn<Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8,TKey9Proxy, TKey9, TSourceProxy, TSource>, TKey3> partColumn3() {
        return getAnyTypeColumn(Part9.PART_COLUMN3, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[2]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Part9#getPartColumn4()}
     */
    public SQLAnyTypeColumn<Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8,TKey9Proxy, TKey9, TSourceProxy, TSource>, TKey4> partColumn4() {
        return getAnyTypeColumn(Part9.PART_COLUMN4, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[3]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Part9#getPartColumn5()}
     */
    public SQLAnyTypeColumn<Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8,TKey9Proxy, TKey9, TSourceProxy, TSource>, TKey5> partColumn5() {
        return getAnyTypeColumn(Part9.PART_COLUMN5, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[4]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Part9#getPartColumn6()}
     */
    public SQLAnyTypeColumn<Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8,TKey9Proxy, TKey9, TSourceProxy, TSource>, TKey6> partColumn6() {
        return getAnyTypeColumn(Part9.PART_COLUMN5, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[5]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Part9#getPartColumn7()}
     */
    public SQLAnyTypeColumn<Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8,TKey9Proxy, TKey9, TSourceProxy, TSource>, TKey6> partColumn7() {
        return getAnyTypeColumn(Part9.PART_COLUMN5, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[6]).map(o -> o.getPropertyType()).orElse(null)));
    }

    /**
     * {@link Part9#getPartColumn8()}
     */
    public SQLAnyTypeColumn<Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8,TKey9Proxy, TKey9, TSourceProxy, TSource>, TKey6> partColumn8() {
        return getAnyTypeColumn(Part9.PART_COLUMN5, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[7]).map(o -> o.getPropertyType()).orElse(null)));
    }


    /**
     * {@link Part9#getPartColumn9()}
     */
    public SQLAnyTypeColumn<Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8,TKey9Proxy, TKey9, TSourceProxy, TSource>, TKey6> partColumn9() {
        return getAnyTypeColumn(Part9.PART_COLUMN5, EasyObjectUtil.typeCastNullable(Optional.ofNullable(getPartByPropTypes()[8]).map(o -> o.getPropertyType()).orElse(null)));
    }

    @Override
    public Class<Part9<TSource, TKey1, TKey2, TKey3, TKey4, TKey5, TKey6, TKey7, TKey8, TKey9>> getEntityClass() {
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
    public Part9Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8,TKey9Proxy, TKey9, TSourceProxy, TSource> create(TableAvailable table, EntitySQLContext entitySQLContext) {
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
        Class<?> key4Class = partColumn4().getPropertyType();
        Class<?> key5Class = partColumn5().getPropertyType();
        Class<?> key6Class = partColumn6().getPropertyType();
        Class<?> key7Class = partColumn7().getPropertyType();
        Class<?> key8Class = partColumn8().getPropertyType();
        Class<?> key9Class = partColumn9().getPropertyType();
        JdbcTypeHandlerManager jdbcTypeHandlerManager = entityTable().getEntitySQLContext().getRuntimeContext().getJdbcTypeHandlerManager();
        JdbcTypeHandler jdbcTypeHandler1 = jdbcTypeHandlerManager.getHandler(key1Class);
        JdbcTypeHandler jdbcTypeHandler2 = jdbcTypeHandlerManager.getHandler(key2Class);
        JdbcTypeHandler jdbcTypeHandler3 = jdbcTypeHandlerManager.getHandler(key3Class);
        JdbcTypeHandler jdbcTypeHandler4 = jdbcTypeHandlerManager.getHandler(key4Class);
        JdbcTypeHandler jdbcTypeHandler5 = jdbcTypeHandlerManager.getHandler(key5Class);
        JdbcTypeHandler jdbcTypeHandler6 = jdbcTypeHandlerManager.getHandler(key6Class);
        JdbcTypeHandler jdbcTypeHandler7 = jdbcTypeHandlerManager.getHandler(key7Class);
        JdbcTypeHandler jdbcTypeHandler8 = jdbcTypeHandlerManager.getHandler(key8Class);
        JdbcTypeHandler jdbcTypeHandler9 = jdbcTypeHandlerManager.getHandler(key9Class);
        Map<String, PartColumn> partColumnMap = new HashMap<>();
        partColumnMap.put(Part9.PART_COLUMN1, new PartColumn(jdbcTypeHandler1, obj -> ((Part9) obj).getPartColumn1(), (obj, value) -> ((Part9) obj).setPartColumn1(value)));
        partColumnMap.put(Part9.PART_COLUMN2, new PartColumn(jdbcTypeHandler2, obj -> ((Part9) obj).getPartColumn2(), (obj, value) -> ((Part9) obj).setPartColumn2(value)));
        partColumnMap.put(Part9.PART_COLUMN3, new PartColumn(jdbcTypeHandler3, obj -> ((Part9) obj).getPartColumn3(), (obj, value) -> ((Part9) obj).setPartColumn3(value)));
        partColumnMap.put(Part9.PART_COLUMN4, new PartColumn(jdbcTypeHandler4, obj -> ((Part9) obj).getPartColumn4(), (obj, value) -> ((Part9) obj).setPartColumn4(value)));
        partColumnMap.put(Part9.PART_COLUMN5, new PartColumn(jdbcTypeHandler5, obj -> ((Part9) obj).getPartColumn5(), (obj, value) -> ((Part9) obj).setPartColumn5(value)));
        partColumnMap.put(Part9.PART_COLUMN6, new PartColumn(jdbcTypeHandler6, obj -> ((Part9) obj).getPartColumn6(), (obj, value) -> ((Part9) obj).setPartColumn6(value)));
        partColumnMap.put(Part9.PART_COLUMN7, new PartColumn(jdbcTypeHandler7, obj -> ((Part9) obj).getPartColumn7(), (obj, value) -> ((Part9) obj).setPartColumn7(value)));
        partColumnMap.put(Part9.PART_COLUMN8, new PartColumn(jdbcTypeHandler8, obj -> ((Part9) obj).getPartColumn8(), (obj, value) -> ((Part9) obj).setPartColumn8(value)));
        partColumnMap.put(Part9.PART_COLUMN9, new PartColumn(jdbcTypeHandler9, obj -> ((Part9) obj).getPartColumn9(), (obj, value) -> ((Part9) obj).setPartColumn9(value)));
        return new PartEntityMetadata(entityClass, entityMetadata, () -> {
            Part9<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> r = new Part9<>();
            Object entity = entityMetadata.getBeanConstructorCreator().get();
            r.setEntity(entity);
            return r;
        }, partColumnMap);
    }
}
