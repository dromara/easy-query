package com.easy.query.core.expression.include.getter;

import com.easy.query.core.common.collection.CollectionDescriptor;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadataFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/3/29 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class EqualsManyToManyMappingRowsGetter extends AbstractIncludeGetter implements RelationIncludeGetter {
    private final QueryRuntimeContext runtimeContext;
    private final String[] targetPropertyNames;
    private final RelationValueColumnMetadataFactory relationValueColumnMetadataFactory;
    private final Map<RelationValue, Collection<RelationExtraEntity>> targetToManyMap;

    public EqualsManyToManyMappingRowsGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes, List<Object> mappingRows) {
        super(navigateMetadata);
        this.runtimeContext = runtimeContext;
        this.relationValueColumnMetadataFactory = runtimeContext.getRelationValueColumnMetadataFactory();
        this.targetPropertyNames = targetPropertyNames;

        this.targetToManyMap = getTargetToManyMap(includes, mappingRows);
    }

    @Override
    public boolean include() {
        return !targetToManyMap.isEmpty();
    }

    @Override
    public Object getIncludeValue(RelationValue relationValue) {
        return targetToManyMap.computeIfAbsent(relationValue, k -> createManyCollection());
    }

    /**
     * 获取对象关系id为key的对象集合为value的map
     *
     * @param includes
     * @param <TNavigateEntity>
     * @return
     */
    protected <TNavigateEntity> Map<RelationValue, Collection<TNavigateEntity>> getTargetToManyMap(List<RelationExtraEntity> includes) {
        CollectionDescriptor collectionDescriptor = EasyClassUtil.getCollectionDescriptorByType(navigateMetadata.getNavigateOriginalPropertyType());
        Map<RelationValue, Collection<TNavigateEntity>> resultMap = new HashMap<>();
        for (RelationExtraEntity target : includes) {
            RelationValue targetRelationId = target.getRelationExtraColumns(targetPropertyNames);
            if (targetRelationId.isNull()) {
                continue;
            }
            Collection<TNavigateEntity> objects = resultMap.computeIfAbsent(targetRelationId, k -> EasyObjectUtil.typeCastNotNull(collectionDescriptor.newCollection()));
            objects.add(EasyObjectUtil.typeCastNullable(target.getEntity()));
        }
        return resultMap;
    }

    protected <TNavigateEntity> Map<RelationValue, Collection<TNavigateEntity>> getTargetToManyMap(List<RelationExtraEntity> includes, List<Object> mappingRows) {

        Map<RelationValue, Collection<TNavigateEntity>> resultMap = new HashMap<>();

        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getMappingClass());
        RelationValueColumnMetadata selfRelationColumn = relationValueColumnMetadataFactory.create(entityMetadata, navigateMetadata.getSelfMappingProperties());
        //优化多级RelationValueColumnMetadata
        RelationValueColumnMetadata targetRelationColumn = relationValueColumnMetadataFactory.create(entityMetadata, navigateMetadata.getTargetMappingProperties());


        Map<RelationValue, Collection<TNavigateEntity>> targetToManyMap = getTargetToManyMap(includes);
        for (Object mappingRow : mappingRows) {
            RelationValue selfRelationId = selfRelationColumn.getRelationValue(mappingRow);
            if (selfRelationId.isNull()) {
                continue;
            }
            RelationValue targetRelationId = targetRelationColumn.getRelationValue(mappingRow);
            if (targetRelationId.isNull()) {
                continue;
            }
            Collection<TNavigateEntity> targetEntities = resultMap.computeIfAbsent(selfRelationId, k -> createManyCollection());
            Collection<TNavigateEntity> targets = targetToManyMap.get(targetRelationId);
            if (EasyCollectionUtil.isNotEmpty(targets)) {
                targetEntities.addAll(targets);
            }
        }
        return resultMap;
    }
}
