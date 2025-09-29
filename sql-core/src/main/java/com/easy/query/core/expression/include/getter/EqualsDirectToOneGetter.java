package com.easy.query.core.expression.include.getter;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadataFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/3/29 21:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class EqualsDirectToOneGetter implements RelationIncludeGetter {
    private final QueryRuntimeContext runtimeContext;
    private final RelationValueColumnMetadataFactory relationValueColumnMetadataFactory;
    private final NavigateMetadata navigateMetadata;
    private final EntityMetadata targetEntityMetadata;
    private final Map<RelationValue, Object> targetToManyMap;

    public EqualsDirectToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata, List<RelationExtraEntity> includes, List<Object> mappingRow) {
        this.runtimeContext = runtimeContext;
        this.relationValueColumnMetadataFactory = runtimeContext.getRelationValueColumnMetadataFactory();
        this.navigateMetadata = navigateMetadata;
        this.targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getNavigatePropertyType());
        this.targetToManyMap = getTargetDirectMap(includes,mappingRow);
    }

    protected Map<RelationValue, Object> getTargetDirectMap(List<RelationExtraEntity> includes, List<Object> mappingRows) {

        Map<RelationValue, Object> resultMap = new HashMap<>();

        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getDirectMappingClass(runtimeContext));
        RelationValueColumnMetadata selfRelationColumn = relationValueColumnMetadataFactory.create(entityMetadata, navigateMetadata.getDirectMappingSelfPropertiesOrPrimary(runtimeContext));
        //优化多级RelationValueColumnMetadata
        RelationValueColumnMetadata targetRelationColumn = relationValueColumnMetadataFactory.createDirect(navigateMetadata, navigateMetadata.getDirectMappingTargetPropertiesOrPrimary(runtimeContext));


        Map<RelationValue, Object> targetDirectMap = getTargetDirectMap(includes);
        for (Object mappingRow : mappingRows) {
            RelationValue selfRelationId = selfRelationColumn.getRelationValue(mappingRow);
            if (selfRelationId.isNull()) {
                continue;
            }
            RelationValue targetRelationId = targetRelationColumn.getRelationValue(mappingRow);
            if (targetRelationId.isNull()) {
                continue;
            }
            Object value = targetDirectMap.get(targetRelationId);
            Object oldVal = resultMap.put(selfRelationId, value);
            if (oldVal != null) {
                //如果你存在NotNull的列这一列的数据可能存在空值,空值之间会互相关联也会导致当前错误,还有一种就是ToOne或者ToMany配置错误
                throw new EasyQueryInvalidOperationException("The relationship property '{" + targetRelationColumn.getPropertyNames() + "}' value ‘" + selfRelationId + "’ appears to have duplicates: ["+EasyClassUtil.getSimpleName(navigateMetadata.getEntityMetadata().getEntityClass())+"." + EasyClassUtil.getInstanceSimpleName(oldVal) + "]. Please confirm whether the data represents a One or Many relationship.");
            }

        }
        return resultMap;
    }


    protected Map<RelationValue, Object> getTargetDirectMap(List<RelationExtraEntity> includes) {
        Map<RelationValue, Object> resultMap = new HashMap<>();
        String[] directTargetPropertiesOrPrimary = navigateMetadata.getDirectTargetPropertiesOrPrimary(runtimeContext);
        for (RelationExtraEntity target : includes) {
            RelationValue targetRelationId = target.getRelationExtraColumns(directTargetPropertiesOrPrimary);
            if (targetRelationId.isNull()) {
                continue;
            }
            resultMap.putIfAbsent(targetRelationId, target.getEntity());
        }
        return resultMap;
    }

    @Override
    public boolean include() {
        return !targetToManyMap.isEmpty();
    }

    @Override
    public Object getIncludeValue(RelationValue relationValue) {
        return targetToManyMap.get(relationValue);
    }

    @Override
    public Object getFlatPaddingValue() {
        return targetEntityMetadata.getBeanConstructorCreator().get();
    }
}
