package com.easy.query.core.expression.include.getter;

import com.easy.query.core.common.collection.CollectionDescriptor;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
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
public class EqualsManyToManyNoMappingRowsGetter extends AbstractIncludeGetter implements RelationIncludeGetter {
    private final String[] targetPropertyNames;
    private final Map<RelationValue, Collection<RelationExtraEntity>> targetToManyMap;
    private final CollectionDescriptor collectionDescriptor;

    public EqualsManyToManyNoMappingRowsGetter(NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        super(navigateMetadata);
        this.targetPropertyNames = targetPropertyNames;
        this.collectionDescriptor = EasyClassUtil.getCollectionDescriptorByType(navigateMetadata.getNavigateOriginalPropertyType());
        this.targetToManyMap = getTargetToManyMap(includes);
    }

    @Override
    public boolean include() {
        return !targetToManyMap.isEmpty();
    }

    @Override
    public Object getIncludeValue(RelationValue relationValue) {
        return targetToManyMap.computeIfAbsent(relationValue, k -> createManyCollection());
    }

    @Override
    public Object getFlatPaddingValue() {
        return collectionDescriptor.newCollection();
    }

    /**
     * 获取对象关系id为key的对象集合为value的map
     *
     * @param includes
     * @param <TNavigateEntity>
     * @return
     */
    protected <TNavigateEntity> Map<RelationValue, Collection<TNavigateEntity>> getTargetToManyMap(List<RelationExtraEntity> includes) {
        Map<RelationValue, Collection<TNavigateEntity>> resultMap = new HashMap<>();
        int i = 0;
        for (RelationExtraEntity target : includes) {
            RelationValue targetRelationId = target.getRelationExtraColumns(targetPropertyNames);
            if (targetRelationId.isNull()) {
                continue;
            }
            Collection<TNavigateEntity> objects = resultMap.computeIfAbsent(targetRelationId, k -> EasyObjectUtil.typeCastNotNull(collectionDescriptor.newCollection()));
            objects.add(EasyObjectUtil.typeCastNullable(target.getEntity()));
            i++;
        }
        return resultMap;
    }
}
