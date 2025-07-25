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
 * create time 2025/3/29 21:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class EqualsOneToManyGetter extends AbstractIncludeGetter implements RelationIncludeGetter{
    private final Class<?> navigateOriginalPropertyType;
    private final String[] targetPropertyNames;
    private final Map<RelationValue, Collection<RelationExtraEntity>> targetToManyMap;
    private final CollectionDescriptor collectionDescriptor;

    public EqualsOneToManyGetter(NavigateMetadata selfNavigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        super(selfNavigateMetadata);
        this.navigateOriginalPropertyType = selfNavigateMetadata.getNavigateOriginalPropertyType();
        this.targetPropertyNames = targetPropertyNames;
        this.collectionDescriptor = EasyClassUtil.getCollectionDescriptorByType(navigateOriginalPropertyType);
        this.targetToManyMap = getTargetToManyMap(includes);
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

        for (RelationExtraEntity target : includes) {
            RelationValue targetRelationId = target.getRelationExtraColumns(targetPropertyNames);
            if(targetRelationId.isNull()){
                continue;
            }
            Collection<TNavigateEntity> objects = resultMap.computeIfAbsent(targetRelationId, k -> EasyObjectUtil.typeCastNotNull(collectionDescriptor.newCollection()));
            objects.add((TNavigateEntity) target.getEntity());
        }
        return resultMap;
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
        return this.collectionDescriptor.newCollection();
    }

}
