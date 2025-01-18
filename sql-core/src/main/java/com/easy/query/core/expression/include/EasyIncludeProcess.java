package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/7/16 18:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyIncludeProcess extends AbstractIncludeProcessor {

    public EasyIncludeProcess(IncludeParserResult includeParserResult, QueryRuntimeContext runtimeContext) {
        super(includeParserResult, runtimeContext);
    }

    @Override
    protected void OneToOneProcess(List<RelationExtraEntity> includes) {
        //获取关联关系列的元信息
        String[] selfRelationColumn = getSelfRelationColumn();
        //因为是一对一所以获取关联数据key为主键的map
        Map<RelationValue, ?> entityMap = EasyCollectionUtil.collectionToMap(entities, x -> x.getRelationExtraColumns(selfRelationColumn), o -> o.getEntity(), (key, old) -> {
            if (old != null) {
                //应该使用ManyToOne而不是OneToOne所以请用户自行确认数据表示的是One-To-One还是Many-To-One
                throw new EasyQueryInvalidOperationException("The relationship value ‘" + key + "’ appears to have duplicates: [" + EasyClassUtil.getInstanceSimpleName(old) + "]. Please confirm whether the data represents a One-To-One or Many-To-One relationship.");
            }
        });
        HashSet<RelationValue> checkToOne = new HashSet<>();
        for (RelationExtraEntity includeEntity : includes) {
            RelationValue subRelationKey = includeEntity.getRelationExtraColumns(targetColumnMetadataPropertyNames);
            if (!checkToOne.add(subRelationKey)) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(selfEntityMetadata.getEntityClass()) + "] target entity:[" + EasyClassUtil.getSimpleName(targetEntityMetadata.getEntityClass()) + "] Please confirm whether the data represents a One-To-One or One-To-Many relationship.");
            }
            Object entity = entityMap.get(subRelationKey);
            if (entity != null) {
                setEntityValue(entity, includeEntity.getEntity());
            }
        }
    }

    @Override
    protected void ManyToOneProcess(List<RelationExtraEntity> includes) {
        //因为是多对一所以获取关联数据key为主键的map
        Map<RelationValue, ?> includeMap = EasyCollectionUtil.collectionToMap(includes, x -> x.getRelationExtraColumns(targetColumnMetadataPropertyNames), o -> o.getEntity(), (key, old) -> {
            if (old != null) {
                //应该使用ManyToOne而不是OneToOne所以请用户自行确认数据表示的是One-To-One还是Many-To-One
                throw new EasyQueryInvalidOperationException("The relationship value ‘" + key + "’ appears to have duplicates: [" + EasyClassUtil.getInstanceSimpleName(old) + "]. Please confirm whether the data represents a Many-To-One or Many-To-Many relationship.");
            }
        });
        String[] selfRelationColumn = getSelfRelationColumn();
        for (RelationExtraEntity entity : entities) {
            RelationValue relationId = entity.getRelationExtraColumns(selfRelationColumn);
            Object entityInclude = includeMap.get(relationId);
            if (entityInclude != null) {
                setEntityValue(entity.getEntity(), entityInclude);
            }
        }
    }

    @Override
    protected void OneToManyProcess(List<RelationExtraEntity> includes) {

        //获取关联关系列的元信息
        String[] selfRelationColumn = getSelfRelationColumn();

        Map<RelationValue, Collection<RelationExtraEntity>> targetToManyMap = getTargetToManyMap(includes);
        for (RelationExtraEntity entity : entities) {
            RelationValue selfRelationId = entity.getRelationExtraColumns(selfRelationColumn);
            Collection<RelationExtraEntity> targetEntities = targetToManyMap.computeIfAbsent(selfRelationId, k -> createManyCollection());
            setEntityValue(entity.getEntity(), targetEntities);
        }
    }


    @Override
    protected void ManyToManyProcess(List<RelationExtraEntity> includes, List<Map<String, Object>> mappingRows) {

        if (includeParserResult.getMappingClass() == null) {

            String[] selfRelationColumn = getSelfRelationColumn();
            Map<RelationValue, Collection<RelationExtraEntity>> targetToManyMap = getTargetToManyMap(includes);
            for (RelationExtraEntity entity : entities) {
                RelationValue selfRelationId = entity.getRelationExtraColumns(selfRelationColumn);
                Collection<RelationExtraEntity> targetEntities = targetToManyMap.computeIfAbsent(selfRelationId, k -> createManyCollection());
                setEntityValue(entity.getEntity(), targetEntities);
            }
        } else {
            Map<RelationValue, Collection<RelationExtraEntity>> targetToManyMap = getTargetToManyMap(includes, mappingRows);
            String[] selfRelationColumn = getSelfRelationColumn();
            for (RelationExtraEntity entity : entities) {
                RelationValue selfRelationId = entity.getRelationExtraColumns(selfRelationColumn);
                Collection<RelationExtraEntity> targetEntities = targetToManyMap.computeIfAbsent(selfRelationId, k -> createManyCollection());
                setEntityValue(entity.getEntity(), targetEntities);
            }
        }
    }

}
