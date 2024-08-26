package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
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
        String selfRelationColumn = getSelfRelationColumn();
        //因为是一对一所以获取关联数据key为主键的map
        Map<?, ?> entityMap = EasyCollectionUtil.collectionToMap(entities, x->x.getRelationExtraColumn(selfRelationColumn), o -> o.getEntity());
        for (RelationExtraEntity includeEntity : includes) {
            Object subRelationKey = includeEntity.getRelationExtraColumn(targetColumnMetadataPropertyName);
            Object entity = entityMap.get(subRelationKey);
            if (entity != null) {
                setEntityValue(entity, includeEntity.getEntity());
            }
        }
    }
    @Override
    protected void ManyToOneProcess(List<RelationExtraEntity> includes) {
        //因为是一对一所以获取关联数据key为主键的map
        Map<Object, ?> includeMap = EasyCollectionUtil.collectionToMap(includes, x->x.getRelationExtraColumn(targetColumnMetadataPropertyName), o -> o.getEntity());
        String selfRelationColumn = getSelfRelationColumn();
        for (RelationExtraEntity entity : entities) {
            Object relationId = entity.getRelationExtraColumn(selfRelationColumn);
            Object entityInclude = includeMap.get(relationId);
            if (entityInclude != null) {
                setEntityValue(entity.getEntity(), entityInclude);
            }
        }
    }

    @Override
    protected void OneToManyProcess(List<RelationExtraEntity> includes) {

        //获取关联关系列的元信息
        String selfRelationColumn = getSelfRelationColumn();

        Map<Object, Collection<RelationExtraEntity>> targetToManyMap = getTargetToManyMap(includes);
        for (RelationExtraEntity entity : entities) {
            Object selfRelationId = entity.getRelationExtraColumn(selfRelationColumn);
            Collection<RelationExtraEntity> targetEntities = targetToManyMap.computeIfAbsent(selfRelationId, k -> createManyCollection());
            setEntityValue(entity.getEntity(),targetEntities);
        }
    }



    @Override
    protected void ManyToManyProcess(List<RelationExtraEntity> includes, List<Map<String, Object>> mappingRows) {

        if(includeParserResult.getMappingClass()==null){

            String selfRelationColumn = getSelfRelationColumn();
            Map<Object, Collection<RelationExtraEntity>> targetToManyMap = getTargetToManyMap(includes);
            for (RelationExtraEntity entity : entities) {
                Object selfRelationId = entity.getRelationExtraColumn(selfRelationColumn);
                Collection<RelationExtraEntity> targetEntities = targetToManyMap.computeIfAbsent(selfRelationId, k -> createManyCollection());
                setEntityValue(entity.getEntity(),targetEntities);
            }
        }else{
            Map<Object, Collection<RelationExtraEntity>> targetToManyMap = getTargetToManyMap(includes,mappingRows);
            String selfRelationColumn = getSelfRelationColumn();
            for (RelationExtraEntity entity : entities) {
                Object selfRelationId =entity.getRelationExtraColumn(selfRelationColumn);
                Collection<RelationExtraEntity> targetEntities = targetToManyMap.computeIfAbsent(selfRelationId,k->createManyCollection());
                setEntityValue(entity.getEntity(), targetEntities);
            }
        }
    }

}
