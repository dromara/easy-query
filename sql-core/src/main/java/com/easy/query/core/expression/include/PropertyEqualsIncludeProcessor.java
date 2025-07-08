package com.easy.query.core.expression.include;

import com.easy.query.core.common.collection.CollectionDescriptor;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadataFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/3/29 20:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyEqualsIncludeProcessor implements PropertyIncludeProcessor {
    protected final Collection<RelationExtraEntity> entities;
    protected final EntityMetadata selfEntityMetadata;
    protected final IncludeParserResult includeParserResult;
    protected final QueryRuntimeContext runtimeContext;
    protected final RelationValueColumnMetadataFactory relationValueColumnMetadataFactory;
    protected final EntityMetadata targetEntityMetadata;
    protected final String[] targetColumnMetadataPropertyNames;

    protected CollectionDescriptor collectionDescriptor;

    public PropertyEqualsIncludeProcessor(IncludeParserResult includeParserResult, QueryRuntimeContext runtimeContext) {
        this.entities = includeParserResult.getRelationExtraEntities();
        this.includeParserResult = includeParserResult;
        this.selfEntityMetadata = includeParserResult.getEntityMetadata();
        this.runtimeContext = runtimeContext;
        this.relationValueColumnMetadataFactory = runtimeContext.getRelationValueColumnMetadataFactory();
        this.targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(includeParserResult.getNavigatePropertyType());
        this.targetColumnMetadataPropertyNames = getTargetRelationColumn();
    }


    private String[] getTargetRelationColumn() {
        return EasyArrayUtil.isEmpty(includeParserResult.getTargetProperties()) ? new String[]{getTargetSingleKeyProperty()} : includeParserResult.getTargetProperties();
    }

    private String getTargetSingleKeyProperty() {
        Collection<String> keyProperties = targetEntityMetadata.getKeyProperties();

        if (EasyCollectionUtil.isNotSingle(keyProperties)) {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(targetEntityMetadata.getEntityClass()) + "multi key not support include");
        }
        return EasyCollectionUtil.first(keyProperties);
    }

    public String[] getSelfRelationColumn() {
        return includeParserResult.getSelfProperties();
    }

    @Override
    public void OneToOneProcess(List<RelationExtraEntity> includes) {
        //获取关联关系列的元信息
        String[] selfRelationColumn = getSelfRelationColumn();
        //因为是一对一所以获取关联数据key为主键的map
        Map<RelationValue, ?> entityMap = EasyCollectionUtil.collectionToMap(entities, x -> {
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
        if(entityMap.isEmpty()){
            return;
        }
        HashSet<RelationValue> checkToOne = new HashSet<>();
        for (RelationExtraEntity includeEntity : includes) {
            RelationValue subRelationKey = includeEntity.getRelationExtraColumns(targetColumnMetadataPropertyNames);
            if(subRelationKey.isNull()){
                continue;
            }
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
    public void DirectToOneProcess(List<RelationExtraEntity> includes) {

        Map<RelationValue, Object> targetToManyMap = getTargetDirectMap(includes, includeParserResult.getMappingRows());
        if(targetToManyMap.isEmpty()){
            return;
        }
        String[] directSelfPropertiesOrPrimary = includeParserResult.getNavigateMetadata().getDirectSelfPropertiesOrPrimary(runtimeContext);
        for (RelationExtraEntity entity : entities) {
            RelationValue selfRelationId = entity.getRelationExtraColumns(directSelfPropertiesOrPrimary);
            if(selfRelationId.isNull()){
                continue;
            }
            Object targetEntity = targetToManyMap.get(selfRelationId);
            if (targetEntity != null) {
                setEntityValue(entity.getEntity(), targetEntity);
            }
        }
    }

    @Override
    public void ManyToOneProcess(List<RelationExtraEntity> includes) {
        String[] selfRelationColumn = getSelfRelationColumn();

        //因为是多对一所以获取关联数据key为主键的map
        Map<RelationValue, ?> includeMap = EasyCollectionUtil.collectionToMap(includes, x -> {
            RelationValue relationExtraColumns = x.getRelationExtraColumns(targetColumnMetadataPropertyNames);
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
        if(includeMap.isEmpty()){
            return;
        }
        for (RelationExtraEntity entity : entities) {
            RelationValue relationId = entity.getRelationExtraColumns(selfRelationColumn);
            if(relationId.isNull()){
                continue;
            }
            Object entityInclude = includeMap.get(relationId);
            if (entityInclude != null) {
                setEntityValue(entity.getEntity(), entityInclude);
            }
        }
    }

    @Override
    public void OneToManyProcess(List<RelationExtraEntity> includes) {

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
    public void ManyToManyProcess(List<RelationExtraEntity> includes, List<Object> mappingRows) {

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

    @Override
    public <T> void setEntityValue(T entity, Object value) {

    }


    /**
     * 获取对象关系id为key的对象集合为value的map
     *
     * @param includes
     * @param <TNavigateEntity>
     * @return
     */
    protected <TNavigateEntity> Map<RelationValue, Collection<TNavigateEntity>> getTargetToManyMap(List<RelationExtraEntity> includes) {
        CollectionDescriptor collectionDescriptor = EasyClassUtil.getCollectionDescriptorByType(includeParserResult.getNavigateOriginalPropertyType());
        Map<RelationValue, Collection<TNavigateEntity>> resultMap = new HashMap<>();

        for (RelationExtraEntity target : includes) {
            RelationValue targetRelationId = target.getRelationExtraColumns(targetColumnMetadataPropertyNames);
            if(targetRelationId.isNull()){
                continue;
            }
            Collection<TNavigateEntity> objects = resultMap.computeIfAbsent(targetRelationId, k -> EasyObjectUtil.typeCastNotNull(collectionDescriptor.newCollection()));
            objects.add(EasyObjectUtil.typeCastNullable(target.getEntity()));
        }
        return resultMap;
    }

    protected Map<RelationValue, Object> getTargetDirectMap(List<RelationExtraEntity> includes) {
        Map<RelationValue, Object> resultMap = new HashMap<>();
        String[] directTargetPropertiesOrPrimary = includeParserResult.getNavigateMetadata().getDirectTargetPropertiesOrPrimary(runtimeContext);
        for (RelationExtraEntity target : includes) {
            RelationValue targetRelationId = target.getRelationExtraColumns(directTargetPropertiesOrPrimary);
            if(targetRelationId.isNull()){
                continue;
            }
            resultMap.putIfAbsent(targetRelationId, target.getEntity());
        }
        return resultMap;
    }

    protected CollectionDescriptor getCollectionDescriptor() {
        if (collectionDescriptor == null) {
            collectionDescriptor = EasyClassUtil.getCollectionDescriptorByType(includeParserResult.getNavigateOriginalPropertyType());
        }
        return collectionDescriptor;
    }

    protected <TNavigateEntity> Collection<TNavigateEntity> createManyCollection() {
        CollectionDescriptor collection = getCollectionDescriptor();
        return EasyObjectUtil.typeCastNotNull(collection.newCollection());
    }

    protected Map<RelationValue, Object> getTargetDirectMap(List<RelationExtraEntity> includes, List<Object> mappingRows) {

        Map<RelationValue, Object> resultMap = new HashMap<>();

        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(includeParserResult.getNavigateMetadata().getDirectMappingClass(runtimeContext));
        RelationValueColumnMetadata selfRelationColumn = relationValueColumnMetadataFactory.create(entityMetadata, includeParserResult.getNavigateMetadata().getDirectMappingSelfPropertiesOrPrimary(runtimeContext));
        //优化多级RelationValueColumnMetadata
        RelationValueColumnMetadata targetRelationColumn = relationValueColumnMetadataFactory.createDirect(includeParserResult.getNavigateMetadata(), includeParserResult.getNavigateMetadata().getDirectMappingTargetPropertiesOrPrimary(runtimeContext));


        Map<RelationValue, Object> targetDirectMap = getTargetDirectMap(includes);
        for (Object mappingRow : mappingRows) {
            RelationValue selfRelationId = selfRelationColumn.getRelationValue(mappingRow);
            if(selfRelationId.isNull()){
                continue;
            }
            RelationValue targetRelationId = targetRelationColumn.getRelationValue(mappingRow);
            if(targetRelationId.isNull()){
                continue;
            }
            Object value = targetDirectMap.get(targetRelationId);
            Object oldVal = resultMap.put(selfRelationId, value);
            if (oldVal != null) {
                //如果你存在NotNull的列这一列的数据可能存在空值,空值之间会互相关联也会导致当前错误,还有一种就是ToOne或者ToMany配置错误
                throw new EasyQueryInvalidOperationException("The relationship property '{" + targetRelationColumn.getPropertyNames() + "}' value ‘" + selfRelationId + "’ appears to have duplicates: [" + EasyClassUtil.getInstanceSimpleName(oldVal) + "]. Please confirm whether the data represents a One or Many relationship.");
            }

        }
        return resultMap;
    }

    protected <TNavigateEntity> Map<RelationValue, Collection<TNavigateEntity>> getTargetToManyMap(List<RelationExtraEntity> includes, List<Object> mappingRows) {

        Map<RelationValue, Collection<TNavigateEntity>> resultMap = new HashMap<>();

        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(includeParserResult.getMappingClass());
        RelationValueColumnMetadata selfRelationColumn = relationValueColumnMetadataFactory.create(entityMetadata, includeParserResult.getSelfMappingProperties());
        //优化多级RelationValueColumnMetadata
        RelationValueColumnMetadata targetRelationColumn = relationValueColumnMetadataFactory.create(entityMetadata, includeParserResult.getTargetMappingProperties());


        Map<RelationValue, Collection<TNavigateEntity>> targetToManyMap = getTargetToManyMap(includes);
        for (Object mappingRow : mappingRows) {
            RelationValue selfRelationId = selfRelationColumn.getRelationValue(mappingRow);
            if(selfRelationId.isNull()){
                continue;
            }
            RelationValue targetRelationId = targetRelationColumn.getRelationValue(mappingRow);
            if(targetRelationId.isNull()){
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
