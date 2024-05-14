package com.easy.query.api.proxy.entity.select.impl;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.FlatEntitySQLContext;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create time 2024/2/24 21:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySelectFlatQueryable<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> {

    private final ClientQueryable<?> queryable;
    private final QueryRuntimeContext runtimeContext;
    private final Property<Object, Collection<?>> navigateGetter;
    private final boolean resultBasicType;
    private final ColumnMetadata columnMetadata;

    public EasySelectFlatQueryable(ClientQueryable<?> queryable, String navValue, TProxy tProxy) {
        if(!(tProxy.getEntitySQLContext() instanceof FlatEntitySQLContext)){
            throw new EasyQueryInvalidOperationException("flatElement result only allowed use in toList");
        }

        this.runtimeContext = queryable.getSQLEntityExpressionBuilder().getRuntimeContext();
        this.resultBasicType = tProxy.getNavValue() == null && tProxy.getValue() != null;
        EntityMetadata entityMetadata = queryable.getSQLEntityExpressionBuilder().getTable(0).getEntityMetadata();
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        EntityMetadata queryEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(queryable.queryClass());


        String[] navValueSplit = navValue.split("\\.");
        String firstNavValue = navValueSplit[0];
        NavigateMetadata firstNavigateMetadata = queryEntityMetadata.getNavigateNotNull(firstNavValue);
        NavigateMetadata currentNavigateMetadata = firstNavigateMetadata;
        EntityMetadata currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
        List<Property<Object, ?>> replyExpressions = new ArrayList<>();
        replyExpressions.add(currentNavigateMetadata.getGetter());
        for (int i = 1; i < navValueSplit.length; i++) {
            String currentNavValue = navValueSplit[i];
            currentNavigateMetadata = currentEntityMetadata.getNavigateNotNull(currentNavValue);
            currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
            replyExpressions.add(currentNavigateMetadata.getGetter());
        }
        if (this.resultBasicType) {
            columnMetadata = currentEntityMetadata.getColumnNotNull(tProxy.getValue());
        } else {
            columnMetadata = null;
        }
        this.navigateGetter = obj -> {
            if (obj == null) {
                return null;
            }
            Iterator<Property<Object, ?>> iterator = replyExpressions.iterator();
            Property<Object, ?> first = iterator.next();
            Collection<Object> collectionValues = getCollectionValue(obj, first);
            while (iterator.hasNext()) {
                Property<Object, ?> getter = iterator.next();
                boolean hasNext = iterator.hasNext();
                collectionValues = collectionValues.stream().map(o -> {
                    return getCollectionValue(o, getter);
                }).flatMap(o -> o.stream()).filter(o -> o != null).map(o -> {
                    return getEndValue(o, hasNext);
                }).distinct().collect(Collectors.toList());
            }
            return collectionValues;
        };
        ClientQueryable<?> select = queryable.select(o -> {
            o.column(firstNavigateMetadata.getSelfPropertyOrPrimary());
//            //todo include
//            EasySQLExpressionUtil.appendTargetExtraTargetProperty(firstNavigateMetadata, sqlEntityExpressionBuilder, o.getAsSelector(), o.getTable());

        });
        selectAutoInclude0(runtimeContext.getEntityMetadataManager(), select, entityMetadata, navValue, tProxy);
        this.queryable = select;
//            }
//        }
    }

    /**
     * 返回最终结果可能是基本类型也可能是对象
     *
     * @param entity
     * @param hasNext
     * @return
     */
    private Object getEndValue(Object entity, boolean hasNext) {
        if (!hasNext && this.resultBasicType) {
            return columnMetadata.getGetterCaller().apply(entity);
        }
        return entity;
    }

    private Collection<Object> getCollectionValue(Object obj, Property<Object, ?> getter) {
        Object value = getter.apply(obj);
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof Collection) {
            return (Collection<Object>) value;
        } else {
            return Collections.singletonList(value);
        }
    }

    private void selectAutoInclude0(EntityMetadataManager entityMetadataManager, ClientQueryable<?> clientQueryable, EntityMetadata entityMetadata, String navigateProperties, TProxy tProxy) {
        if (EasyStringUtil.isBlank(navigateProperties)) {
            return;
        }
        int i = navigateProperties.indexOf(".");
        String navigateProperty = i >= 0 ? navigateProperties.substring(0, i) : navigateProperties;
        String nextNavigateProperty = i >= 0 ? navigateProperties.substring(i + 1) : null;


        clientQueryable
                .include(t -> {
                    ClientQueryable<Object> with = t.with(navigateProperty);
                    NavigateMetadata navigateMetadata = entityMetadata.getNavigateNotNull(navigateProperty);
                    EntityMetadata entityEntityMetadata = entityMetadataManager.getEntityMetadata(navigateMetadata.getNavigatePropertyType());
                    selectAutoInclude0(entityMetadataManager, with, entityEntityMetadata, nextNavigateProperty, tProxy);
                    //没有下级要拉去
                    if (nextNavigateProperty == null) {
                        if (resultBasicType) {
                            return with.select(x -> {
                                x.column(tProxy.getValue());
                            });
                        }
                        if(tProxy.getEntitySQLContext() instanceof FlatEntitySQLContext){
                            FlatEntitySQLContext flatEntitySQLContext = (FlatEntitySQLContext)tProxy.getEntitySQLContext() ;
                            if (flatEntitySQLContext.getSelectAsExpressionFunction() != null) {
                                Class<Object> queryClass = with.queryClass();
                                return with.select(x -> {
                                    SQLSelectAsExpression sqlSelectAsExpression = flatEntitySQLContext.getSelectAsExpressionFunction().apply(
                                            EasyObjectUtil.typeCastNullable(
                                                    EntityQueryProxyManager.create(EasyObjectUtil.typeCastNullable(queryClass)).create(x.getTable(), with.getSQLEntityExpressionBuilder(), x.getRuntimeContext())
                                            )
                                    );
                                    sqlSelectAsExpression.accept(x.getSelector());
                                });
                            }
                        }
                        return with;
                    } else {
                        return with.select(c -> {
                            Collection<String> keyProperties = entityMetadata.getKeyProperties();
                            for (String keyProperty : keyProperties) {
                                c.column(keyProperty);
                            }
                        });
                    }
                });
    }

    public <TR> List<TR> toList() {
        List<?> entities = queryable.toList(queryable.queryClass());
        return entities.stream().map(o -> {
            return this.<Collection<TR>>getNavigates(o);
        }).flatMap(o -> o.stream()).filter(o -> o != null).distinct().collect(Collectors.toList());
    }

    protected <TResult> TResult getNavigates(Object entity) {
        if (entity != null) {
            Collection<?> values = navigateGetter.apply(entity);
            if (values == null) {
                return null;
            }
            return EasyObjectUtil.typeCastNullable(values);
        }
        return null;
    }
}
