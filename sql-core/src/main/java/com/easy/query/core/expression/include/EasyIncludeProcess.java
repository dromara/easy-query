package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
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

    public EasyIncludeProcess(Collection<?> entities, NavigateMetadata selfNavigateMetadata, QueryRuntimeContext runtimeContext) {
        super(entities, selfNavigateMetadata, runtimeContext);
    }

    @Override
    protected <TEntityInclude> void OneToOneProcess(List<TEntityInclude> includes) {
        //获取关联关系列的元信息
        ColumnMetadata selfRelationColumn = selfNavigateMetadata.getSelfRelationColumn();
        Property<Object, ?> keyGetter = selfRelationColumn.getGetterCaller();
        //因为是一对一所以获取关联数据key为主键的map
        Map<?, ?> entityMap = EasyCollectionUtil.collectionToMap(entities, keyGetter::apply, o -> o);
        for (Object includeEntity : includes) {
            Object subRelationKey = targetColumnMetadata.getGetterCaller().apply(includeEntity);
            Object entity = entityMap.get(subRelationKey);
            if (entity != null) {
                selfNavigateMetadata.getSetter().call(entity, includeEntity);
            }
        }
    }
    @Override
    protected <TEntityInclude> void ManyToOneProcess(List<TEntityInclude> includes) {
        Property<Object, ?> getterCaller = targetColumnMetadata.getGetterCaller();
        //因为是一对一所以获取关联数据key为主键的map
        Map<Object, TEntityInclude> includeMap = EasyCollectionUtil.collectionToMap(includes, getterCaller::apply, o -> o);
        ColumnMetadata selfRelationColumn = selfNavigateMetadata.getSelfRelationColumn();
        for (Object entity : entities) {
            Object relationId = selfRelationColumn.getGetterCaller().apply(entity);
            TEntityInclude entityInclude = includeMap.get(relationId);
            if (entityInclude != null) {
                selfNavigateMetadata.getSetter().call(entity, entityInclude);
            }
        }
    }

    @Override
    protected <TEntityInclude> void OneToManyProcess(List<TEntityInclude> includes) {

        //获取关联关系列的元信息
        ColumnMetadata selfRelationColumn = selfNavigateMetadata.getSelfRelationColumn();
        //因为是一对多所以获取关联数据key为主键的map
//        Map<?, ?> entityMap = EasyCollectionUtil.collectionToMap(entities, keyGetter::apply, o -> o);

        Map<Object, Collection<TEntityInclude>> targetToManyMap = getTargetToManyMap(includes);
//        Set<Map.Entry<Object, Collection<TEntityInclude>>> entries = targetToManyMap.entrySet();
//        for (Map.Entry<Object, Collection<TEntityInclude>> entry : entries) {
//            Object subRelationKey = entry.getKey();
//            Collection<TEntityInclude> subValues = entry.getValue();
//            Object tr = entityMap.get(subRelationKey);
//            if (tr != null) {
//                selfNavigateMetadata.getSetter().call(tr, subValues);
//            }
//        }
        for (Object entity : entities) {
            Object selfRelationId = selfRelationColumn.getGetterCaller().apply(entity);
            Collection<TEntityInclude> targetEntities = targetToManyMap.computeIfAbsent(selfRelationId, k -> createManyCollection());
            selfRelationColumn.getSetterCaller().call(entity,targetEntities);
        }
    }



    @Override
    protected <TEntityInclude> void ManyToManyProcess(List<TEntityInclude> includes, List<Map<String, Object>> mappingRows) {

        Map<Object, Collection<TEntityInclude>> targetToManyMap = getTargetToManyMap(includes,mappingRows);
        ColumnMetadata selfRelationColumn = selfNavigateMetadata.getSelfRelationColumn();
        for (Object entity : entities) {
            Object selfRelationId = selfRelationColumn.getGetterCaller().apply(entity);
            Collection<TEntityInclude> targetEntities = targetToManyMap.computeIfAbsent(selfRelationId,k->createManyCollection());
            selfNavigateMetadata.getSetter().call(entity, targetEntities);
        }
    }

}
