package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationNullValueValidator;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadata;
import com.easy.query.core.expression.sql.include.relation.RelationValueColumnMetadataFactory;
import com.easy.query.core.expression.sql.include.relation.RelationValueFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/7/16 18:29
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractIncludeProcessor implements IncludeProcessor {
    protected final Collection<RelationExtraEntity> entities;
    protected final EntityMetadata selfEntityMetadata;
    protected final IncludeParserResult includeParserResult;
    protected final QueryRuntimeContext runtimeContext;
    protected final RelationValueColumnMetadataFactory relationValueColumnMetadataFactory;
    protected final EntityMetadata targetEntityMetadata;
    protected final String[] targetColumnMetadataPropertyNames;

    protected Class<?> collectionType;

    public AbstractIncludeProcessor(IncludeParserResult includeParserResult, QueryRuntimeContext runtimeContext) {
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

    /**
     * 获取对象关系id为key的对象集合为value的map
     *
     * @param includes
     * @param <TNavigateEntity>
     * @return
     */
    protected <TNavigateEntity> Map<RelationValue, Collection<TNavigateEntity>> getTargetToManyMap(List<RelationExtraEntity> includes) {
        Class<?> collectionType = EasyClassUtil.getCollectionImplType(includeParserResult.getNavigateOriginalPropertyType());
        Map<RelationValue, Collection<TNavigateEntity>> resultMap = new HashMap<>();

        for (RelationExtraEntity target : includes) {
            RelationValue targetRelationId = target.getRelationExtraColumns(targetColumnMetadataPropertyNames);
            if(targetRelationId.isNull()){
                continue;
            }
            Collection<TNavigateEntity> objects = resultMap.computeIfAbsent(targetRelationId, k -> (Collection<TNavigateEntity>) EasyClassUtil.newInstance(collectionType));
            objects.add((TNavigateEntity) target.getEntity());
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

    protected Class<?> getCollectionType() {
        if (collectionType == null) {
            collectionType = EasyClassUtil.getCollectionImplType(includeParserResult.getNavigateOriginalPropertyType());
        }
        return collectionType;
    }

    protected <TNavigateEntity> Collection<TNavigateEntity> createManyCollection() {
        Class<?> collectionType = getCollectionType();
        return EasyObjectUtil.typeCastNullable(EasyClassUtil.newInstance(collectionType));
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
//    protected <TNavigateEntity> Map<Object, Collection<TNavigateEntity>> getTargetDirectToManyMap(List<RelationExtraEntity> includes, List<Map<String, Object>> mappingRows) {
//
//        Map<Object, Collection<TNavigateEntity>> resultMap = new HashMap<>();
//
//        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(includeParserResult.getMappingClass());
//        ColumnMetadata selfRelationColumn = entityMetadata.getColumnNotNull(includeParserResult.getSelfMappingProperties());
//        String selfColumnName = selfRelationColumn.getName();
//        ColumnMetadata targetRelationColumn = entityMetadata.getColumnNotNull(includeParserResult.getTargetMappingProperties());
//        String targetColumnName = targetRelationColumn.getName();
//
//        Map<Object, Collection<TNavigateEntity>> targetToManyMap = getTargetToManyMap(includes);
//        for (Map<String, Object> mappingRow : mappingRows) {
//            Object selfRelationId = mappingRow.get(selfColumnName);
//            Object targetRelationId = mappingRow.get(targetColumnName);
//            Collection<TNavigateEntity> targetEntities = resultMap.computeIfAbsent(selfRelationId, k -> createManyCollection());
//            Collection<TNavigateEntity> targets = targetToManyMap.get(targetRelationId);
//            if(EasyCollectionUtil.isNotEmpty(targets)){
//                targetEntities.addAll(targets);
//            }
//        }
//        return resultMap;
//    }
//    protected Map<Object, Collection<Object>> getSelfToManyMap() {
//        Map<Object, Collection<Object>> resultMap = new HashMap<>();
//        ColumnMetadata selfRelationColumn = includeParserResult.getEntityMetadata().getColumnNotNull(includeParserResult.getSelfProperty());
//        for (Object entity : entities) {
//            Object relationId = selfRelationColumn.getGetterCaller().apply(entity);
//            Collection<Object> objects = resultMap.computeIfAbsent(relationId, k -> new ArrayList<>());
//            objects.add(entity);
//        }
//        return resultMap;
//    }

    @Override
    public void process() {
        switch (includeParserResult.getRelationType()) {
            case OneToOne:
                if (EasyArrayUtil.isNotEmpty(includeParserResult.getDirectMapping())) {
                    DirectToOneProcess(includeParserResult.getIncludeResult());
                } else {
                    OneToOneProcess(includeParserResult.getIncludeResult());
                }
                return;
            case OneToMany:
                OneToManyProcess(includeParserResult.getIncludeResult());
                return;
            case ManyToOne:
                if (EasyArrayUtil.isNotEmpty(includeParserResult.getDirectMapping())) {
                    DirectToOneProcess(includeParserResult.getIncludeResult());
                } else {
                    ManyToOneProcess(includeParserResult.getIncludeResult());
                }
                return;
            case ManyToMany:
                ManyToManyProcess(includeParserResult.getIncludeResult(), includeParserResult.getMappingRows());
                return;
        }
        throw new UnsupportedOperationException("not support include relation type:" + includeParserResult.getRelationType());
    }

    protected abstract void OneToOneProcess(List<RelationExtraEntity> includes);

    protected abstract void DirectToOneProcess(List<RelationExtraEntity> includes);

    protected abstract void ManyToOneProcess(List<RelationExtraEntity> includes);

    protected abstract void OneToManyProcess(List<RelationExtraEntity> includes);

    protected abstract void ManyToManyProcess(List<RelationExtraEntity> includes, List<Object> mappingRows);


    protected <T> void setEntityValue(T entity, Object value) {
        includeParserResult.getSetter().call(entity, value);
    }

    public String[] getSelfRelationColumn() {
        return includeParserResult.getSelfProperties();
    }
}
