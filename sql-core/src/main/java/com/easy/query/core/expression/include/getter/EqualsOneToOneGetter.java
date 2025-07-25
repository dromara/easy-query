package com.easy.query.core.expression.include.getter;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.Map;

/**
 * create time 2025/3/29 21:06
 * 文件说明
 *
 * @author xuejiaming
 */
public class EqualsOneToOneGetter implements RelationIncludeGetter{
    private final Map<RelationValue, ?> entityMap;
    private final EntityMetadata targetEntityMetadata;

    public EqualsOneToOneGetter(QueryRuntimeContext runtimeContext, NavigateMetadata navigateMetadata,String[] selfRelationColumn, Collection<RelationExtraEntity> entities) {

        //因为是一对一所以获取关联数据key为主键的map
        this.entityMap = EasyCollectionUtil.collectionToMap(entities, x -> {
            RelationValue relationExtraColumns = x.getRelationExtraColumns(selfRelationColumn);
            if(relationExtraColumns.isNull()){
                return null;
            }
            return relationExtraColumns;
        }, o -> o.getEntity(), (key, old) -> {
            if (old != null) {
                //应该使用ManyToOne而不是OneToOne所以请用户自行确认数据表示的是One-To-One还是Many-To-One
                throw new EasyQueryInvalidOperationException("The relationship value ‘" + key + "’ appears to have duplicates: [" + EasyClassUtil.getInstanceSimpleName(old) + "]. Please confirm whether the data represents a One or Many relationship.");
            }
        });
        this.targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getNavigatePropertyType());
    }

    @Override
    public boolean include() {
        return !this.entityMap.isEmpty();
    }

    @Override
    public Object getIncludeValue(RelationValue relationValue) {
        return entityMap.get(relationValue);
    }

    @Override
    public Object getFlatPaddingValue() {
        return targetEntityMetadata.getBeanConstructorCreator().get();
    }
}
