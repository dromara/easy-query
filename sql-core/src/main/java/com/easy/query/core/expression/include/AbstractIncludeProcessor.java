package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.MappingPathIterator;
import com.easy.query.core.metadata.NavigateFlatMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    protected final EntityMetadata targetEntityMetadata;
    protected final String targetColumnMetadataPropertyName;

    protected Class<?> collectionType;
    private  Property<Object, Collection<?>> navigateFlatGetter;

    public AbstractIncludeProcessor(IncludeParserResult includeParserResult, QueryRuntimeContext runtimeContext) {
        this.entities = includeParserResult.getRelationExtraEntities();
        this.includeParserResult = includeParserResult;
        this.selfEntityMetadata = includeParserResult.getEntityMetadata();
        this.runtimeContext = runtimeContext;
        this.targetEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(includeParserResult.getNavigatePropertyType());
       this.targetColumnMetadataPropertyName = getTargetRelationColumn();
        initNavigateFlatGetter(includeParserResult.getFlatQueryEntityMetadata(),includeParserResult.getNavigateFlatMetadata(),runtimeContext);
    }

    public void initNavigateFlatGetter(EntityMetadata flatQueryEntityMetadata,NavigateFlatMetadata navigateFlatMetadata, QueryRuntimeContext runtimeContext){
        if(navigateFlatMetadata==null){
            return;
        }
        boolean basicType = navigateFlatMetadata.isBasicType();
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        MappingPathIterator mappingPathIterator = navigateFlatMetadata.getMappingPathIterator();
        String firstNavValue = mappingPathIterator.next();
        List<Property<Object, ?>> replyExpressions = new ArrayList<>();
        NavigateMetadata currentNavigateMetadata = flatQueryEntityMetadata.getNavigateNotNull(firstNavValue);
        EntityMetadata currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
//        replyExpressions.add(currentNavigateMetadata.getGetter());
        while(mappingPathIterator.hasNext()){
            String currentNavValue = mappingPathIterator.next();
            if(!mappingPathIterator.hasNext()){
                if(basicType){
                    ColumnMetadata columnMetadata = currentEntityMetadata.getColumnNotNull(currentNavValue);
                    replyExpressions.add(columnMetadata.getGetterCaller());
                    break;
                }
            }
            currentNavigateMetadata = currentEntityMetadata.getNavigateNotNull(currentNavValue);
            currentEntityMetadata = entityMetadataManager.getEntityMetadata(currentNavigateMetadata.getNavigatePropertyType());
            replyExpressions.add(currentNavigateMetadata.getGetter());
        }
        this.navigateFlatGetter = obj -> {
            if (obj == null) {
                return null;
            }
            Iterator<Property<Object, ?>> iterator = replyExpressions.iterator();
            Property<Object, ?> first = iterator.next();
            Collection<Object> collectionValues = getCollectionValue(obj, first);
            while (iterator.hasNext()) {
                Property<Object, ?> getter = iterator.next();
                collectionValues = collectionValues.stream().map(o -> {
                    return getCollectionValue(o, getter);
                }).flatMap(o -> o.stream()).filter(o -> o != null).distinct().collect(Collectors.toList());
            }
            return collectionValues;
        };
    }

    private Collection<Object> getCollectionValue(Object obj, Property<Object, ?> getter) {
        Object value = getter.apply(obj);
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof Collection) {
            return (Collection<Object>) value;
        } else {
            return Collections.singletonList(value);
        }
    }



    private String getTargetRelationColumn(){
        return EasyStringUtil.isBlank(includeParserResult.getTargetProperty()) ? getTargetSingleKeyProperty() : includeParserResult.getTargetProperty();
    }
    private String getTargetSingleKeyProperty(){
        Collection<String> keyProperties = targetEntityMetadata.getKeyProperties();

        if (EasyCollectionUtil.isNotSingle(keyProperties)) {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(targetEntityMetadata.getEntityClass()) + "multi key not support include");
        }
        return EasyCollectionUtil.first(keyProperties);
    }

    /**
     * 获取对象关系id为key的对象集合为value的map
     * @param includes
     * @return
     * @param <TNavigateEntity>
     */
    protected <TNavigateEntity> Map<Object, Collection<TNavigateEntity>> getTargetToManyMap(List<RelationExtraEntity> includes) {
        Class<?> collectionType = EasyClassUtil.getCollectionImplType(includeParserResult.getNavigateOriginalPropertyType());
        Map<Object, Collection<TNavigateEntity>> resultMap = new HashMap<>();
        for (RelationExtraEntity target : includes) {
            Object targetRelationId = target.getRelationExtraColumn(targetColumnMetadataPropertyName);
            Collection<TNavigateEntity> objects = resultMap.computeIfAbsent(targetRelationId, k -> (Collection<TNavigateEntity>) EasyClassUtil.newInstance(collectionType));
            objects.add((TNavigateEntity)target.getEntity());
        }
        return resultMap;
    }

    protected Class<?> getCollectionType(){
        if(collectionType==null){
            collectionType = EasyClassUtil.getCollectionImplType(includeParserResult.getNavigateOriginalPropertyType());
        }
        return collectionType;
    }
    protected <TNavigateEntity> Collection<TNavigateEntity> createManyCollection(){
        Class<?> collectionType = getCollectionType();
        return EasyObjectUtil.typeCastNullable(EasyClassUtil.newInstance(collectionType));
    }
    protected <TNavigateEntity> Map<Object, Collection<TNavigateEntity>> getTargetToManyMap(List<RelationExtraEntity> includes, List<Map<String, Object>> mappingRows) {

        Map<Object, Collection<TNavigateEntity>> resultMap = new HashMap<>();

        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(includeParserResult.getMappingClass());
        ColumnMetadata selfRelationColumn = entityMetadata.getColumnNotNull(includeParserResult.getSelfMappingProperty());
        String selfColumnName = selfRelationColumn.getName();
        ColumnMetadata targetRelationColumn = entityMetadata.getColumnNotNull(includeParserResult.getTargetMappingProperty());
        String targetColumnName = targetRelationColumn.getName();

        Map<Object, Collection<TNavigateEntity>> targetToManyMap = getTargetToManyMap(includes);
        for (Map<String, Object> mappingRow : mappingRows) {
            Object selfRelationId = mappingRow.get(selfColumnName);
            Object targetRelationId = mappingRow.get(targetColumnName);
            Collection<TNavigateEntity> targetEntities = resultMap.computeIfAbsent(selfRelationId, k -> createManyCollection());
            Collection<TNavigateEntity> targets = targetToManyMap.get(targetRelationId);
            if(EasyCollectionUtil.isNotEmpty(targets)){
                targetEntities.addAll(targets);
            }
        }
        return resultMap;
    }
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
                OneToOneProcess(includeParserResult.getIncludeResult());
                return;
            case OneToMany:
                OneToManyProcess(includeParserResult.getIncludeResult());
                return;
            case ManyToOne:
                ManyToOneProcess(includeParserResult.getIncludeResult());
                return;
            case ManyToMany:
                ManyToManyProcess(includeParserResult.getIncludeResult(),includeParserResult.getMappingRows());
                return;
        }
        throw new UnsupportedOperationException("not support include relation type:" + includeParserResult.getRelationType());
    }

    protected abstract void OneToOneProcess(List<RelationExtraEntity> includes);

    protected abstract void ManyToOneProcess(List<RelationExtraEntity> includes);

    protected abstract void OneToManyProcess(List<RelationExtraEntity> includes);

    protected abstract void ManyToManyProcess(List<RelationExtraEntity> includes, List<Map<String, Object>> mappingRows);


    protected <T> void setEntityValue(T entity,Object value){
        if(includeParserResult.getNavigateFlatMetadata()!=null){
            if(value instanceof Collection){
                Collection<?> values = ((Collection<?>) value).stream().map(o->navigateFlatGetter.apply(o)).flatMap(o->o.stream()).distinct().collect(Collectors.toList());
                includeParserResult.getNavigateFlatMetadata().getBeanSetter().call(entity,values);
            }else{
                Collection<?> values = navigateFlatGetter.apply(value);
                includeParserResult.getNavigateFlatMetadata().getBeanSetter().call(entity,EasyCollectionUtil.firstOrNull(values));
            }
        }else{
            includeParserResult.getSetter().call(entity,value);
        }
//        if(includeParseContext.getIncludeNavigateParams().isMappingFlat()){
//            System.out.println("1");
//        }else{
//        }
    }

    public String getSelfRelationColumn() {
        return includeParserResult.getSelfProperty();
    }
}
