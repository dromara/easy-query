package com.easy.query.core.expression.include.getter;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadataFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.Comparator;
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

    public EqualsManyToManyNoMappingRowsGetter(NavigateMetadata navigateMetadata, String[] targetPropertyNames, List<RelationExtraEntity> includes) {
        super(navigateMetadata);
        this.targetPropertyNames = targetPropertyNames;
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

    /**
     * 获取对象关系id为key的对象集合为value的map
     *
     * @param includes
     * @param <TNavigateEntity>
     * @return
     */
    protected <TNavigateEntity> Map<RelationValue, Collection<TNavigateEntity>> getTargetToManyMap(List<RelationExtraEntity> includes) {
        Class<?> collectionType = EasyClassUtil.getCollectionImplType(navigateMetadata.getNavigateOriginalPropertyType());
        Map<RelationValue, Collection<TNavigateEntity>> resultMap = new HashMap<>();
        int i = 0;
        for (RelationExtraEntity target : includes) {
            RelationValue targetRelationId = target.getRelationExtraColumns(targetPropertyNames);
            if (targetRelationId.isNull()) {
                continue;
            }
            Collection<TNavigateEntity> objects = resultMap.computeIfAbsent(targetRelationId, k -> (Collection<TNavigateEntity>) EasyClassUtil.newInstance(collectionType));
            objects.add((TNavigateEntity) target.getEntity());
            i++;
        }
        return resultMap;
    }
}
