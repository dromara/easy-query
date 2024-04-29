package com.easy.query.api.proxy.entity.select.impl;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ProxyEntity;
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

    public EasySelectFlatQueryable(ClientQueryable<?> queryable,  String navValue) {

        this.runtimeContext = queryable.getSQLEntityExpressionBuilder().getRuntimeContext();
        EntityMetadata entityMetadata = queryable.getSQLEntityExpressionBuilder().getTable(0).getEntityMetadata();
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        EntityMetadata queryEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(queryable.queryClass());
        String[] navValueSplit = navValue.split("\\.");
        String firstNavValue = navValueSplit[0];
        NavigateMetadata currentNavigateMetadata = queryEntityMetadata.getNavigateNotNull(firstNavValue);
        EntityMetadata currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
        List<Property<Object, ?>> replyExpressions = new ArrayList<>();
        replyExpressions.add(currentNavigateMetadata.getGetter());
        for (int i = 1; i < navValueSplit.length; i++) {
            String currentNavValue = navValueSplit[i];
            currentNavigateMetadata = currentEntityMetadata.getNavigateNotNull(currentNavValue);
            currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
            replyExpressions.add(currentNavigateMetadata.getGetter());
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
                collectionValues= collectionValues.stream().map(o -> {
                    return  getCollectionValue(o, getter);
                }).flatMap(o -> o.stream()).filter(o -> o != null).distinct().collect(Collectors.toList());
            }
            return collectionValues;
        };
        selectAutoInclude0(runtimeContext.getEntityMetadataManager(), queryable, entityMetadata, navValue);
        this.queryable = queryable;
//            }
//        }
    }

    private Collection<Object> getCollectionValue(Object obj,Property<Object, ?> getter){
        Object value = getter.apply(obj);
        if(value==null){
            return Collections.emptyList();
        }
        if(value instanceof Collection){
            return (Collection<Object>) value;
        }else{
            return Collections.singletonList(value);
        }
    }

    private void selectAutoInclude0(EntityMetadataManager entityMetadataManager, ClientQueryable<?> clientQueryable, EntityMetadata entityMetadata, String navigateProperties) {
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
                    selectAutoInclude0(entityMetadataManager, with, entityEntityMetadata, nextNavigateProperty);
                    return with;
                });
    }

    public <TR> List<TR> toList() {
        List<?> entities = queryable.toList(queryable.queryClass());
        return entities.stream().map(o -> {
            return this.<Collection<TR>>getNavigates(o);
        }).flatMap(o -> o.stream()).filter(o -> o != null).distinct().collect(Collectors.toList());
    }

    protected  <TResult> TResult getNavigates(Object entity) {
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
