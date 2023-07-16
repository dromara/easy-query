package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/7/16 18:29
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractIncludeProcessor implements IncludeProcessor {
    protected final Collection<?> entities;
    protected final EntityMetadata selfEntityMetadata;
    protected final NavigateMetadata selfNavigateMetadata;
    protected final QueryRuntimeContext runtimeContext;
    protected final EntityMetadata targetEntityMetadata;
    protected final ColumnMetadata targetColumnMetadata;

    public AbstractIncludeProcessor(Collection<?> entities, NavigateMetadata selfNavigateMetadata, QueryRuntimeContext runtimeContext) {

        this.entities = entities;
        this.selfNavigateMetadata = selfNavigateMetadata;
        this.selfEntityMetadata = selfNavigateMetadata.getEntityMetadata();
        this.runtimeContext = runtimeContext;
        this.targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(selfNavigateMetadata.getNavigatePropertyType());
        this.targetColumnMetadata = getTargetRelationColumn();
    }



    private ColumnMetadata getTargetRelationColumn(){
        String selfPropertyName = EasyStringUtil.isBlank(selfNavigateMetadata.getTargetProperty()) ? getTargetSingleKeyProperty() : selfNavigateMetadata.getTargetProperty();
        return targetEntityMetadata.getColumnNotNull(selfPropertyName);
    }
    private String getTargetSingleKeyProperty(){
        Collection<String> keyProperties = targetEntityMetadata.getKeyProperties();

        if (EasyCollectionUtil.isNotSingle(keyProperties)) {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(targetEntityMetadata.getEntityClass()) + "multi key not support include");
        }
        return EasyCollectionUtil.first(keyProperties);
    }

    /**
     * 获取对象关系id为key的对象集合为value的map
     * @param includes
     * @return
     * @param <TNavigateEntity>
     */
    protected <TNavigateEntity> Map<Object, Collection<TNavigateEntity>> getTargetToManyMap(List<TNavigateEntity> includes) {
        Class<?> collectionType = EasyClassUtil.getCollectionImplType(selfNavigateMetadata.getNavigateOriginalPropertyType());
        Map<Object, Collection<TNavigateEntity>> resultMap = new HashMap<>();
        for (TNavigateEntity tNavigateEntity : includes) {
            Object subRelationKey = targetColumnMetadata.getGetterCaller().apply(tNavigateEntity);
            Collection<TNavigateEntity> objects = resultMap.computeIfAbsent(subRelationKey, k -> (Collection<TNavigateEntity>) EasyClassUtil.newInstance(collectionType));
            objects.add(tNavigateEntity);
        }
        return resultMap;
    }
    protected Map<Object, Collection<Object>> getSelfToManyMap() {
        Map<Object, Collection<Object>> resultMap = new HashMap<>();
        ColumnMetadata selfRelationColumn = selfNavigateMetadata.getSelfRelationColumn();
        for (Object entity : entities) {
            Object relationId = selfRelationColumn.getGetterCaller().apply(entity);
            Collection<Object> objects = resultMap.computeIfAbsent(relationId, k -> new ArrayList<>());
            objects.add(entity);
        }
        return resultMap;
    }

    @Override
    public <TEntityInclude> void process(List<TEntityInclude> includes) {
        if(EasyCollectionUtil.isEmpty(includes)){
            return;
        }
        switch (selfNavigateMetadata.getRelationType()) {
            case OneToOne:
                OneToOneProcess(includes);
                return;
            case OneToMany:
                OneToManyProcess(includes);
                return;
            case ManyToOne:
                ManyToOneProcess(includes);
                return;
            case ManyToMany:
                ManyToManyProcess(includes);
                return;
        }
        throw new UnsupportedOperationException("not support include relation type:" + selfNavigateMetadata.getRelationType());
    }

    protected abstract <TEntityInclude> void OneToOneProcess(List<TEntityInclude> includes);

    protected abstract <TEntityInclude> void ManyToOneProcess(List<TEntityInclude> includes);

    protected abstract <TEntityInclude> void OneToManyProcess(List<TEntityInclude> includes);

    protected abstract<TEntityInclude> void ManyToManyProcess(List<TEntityInclude> includes);
}
