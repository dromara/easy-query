package com.easy.query.core.expression.include;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.expression.include.getter.RelationIncludeGetter;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        RelationIncludeGetter oneToOneGetter = selfNavigateMetadata.getEntityRelationPropertyProvider().getOneToOneGetter(runtimeContext, selfNavigateMetadata, selfRelationColumn, entities);
        if (oneToOneGetter == null) {
            throw new EasyQueryInvalidOperationException("Please implement the getOneToOneGetter method first.");
        }


        if (!oneToOneGetter.include()) {
            return;
        }
        Class<?> flatClassType = includeParserResult.getIncludeNavigateParams().getFlatClassType();
        Map<Object, Object> flatClassMap = includeParserResult.getFlatClassMap();
        HashSet<RelationValue> checkToOne = new HashSet<>();
        for (RelationExtraEntity includeEntity : includes) {
            RelationValue subRelationKey = includeEntity.getRelationExtraColumns(targetColumnMetadataPropertyNames);
            if (subRelationKey.isNull()) {
                continue;
            }
            if (!checkToOne.add(subRelationKey)) {
                throw new EasyQueryInvalidOperationException("entity:[" + EasyClassUtil.getSimpleName(selfEntityMetadata.getEntityClass()) + "] target entity:[" + EasyClassUtil.getSimpleName(targetEntityMetadata.getEntityClass()) + "] Please confirm whether the data represents a One-To-One or One-To-Many relationship.");
            }
            Object entity = oneToOneGetter.getIncludeValue(subRelationKey);
            if (entity != null) {
                //如果报错cast那么请确认是否保留了最后一层的集合
                //NavigateFlat 展开最后一层后如果使用了ManyToOne或者OneToOne的对象那么必须保留最后一层集合不可以直接Flat到对象
//                setEntityValue(entity, includeEntity.getEntity());
                if (flatClassType != null) {//特殊处理
                    Object flatPaddingValue = oneToOneGetter.getFlatPaddingValue();
                    flatClassMap.put(flatPaddingValue, includeEntity.getEntity());
                    setEntityValue(entity, flatPaddingValue);
                } else {
                    setEntityValue(entity, includeEntity.getEntity());
                }
            }
        }
    }

    @Override
    protected void DirectToOneProcess(List<RelationExtraEntity> includes) {

        RelationIncludeGetter directToOneGetter = selfNavigateMetadata.getEntityRelationPropertyProvider().getDirectToOneGetter(runtimeContext, selfNavigateMetadata, includes, includeParserResult.getMappingRows());
        if (directToOneGetter == null) {
            throw new EasyQueryInvalidOperationException("Please implement the getDirectToOneGetter method first.");
        }

        if (!directToOneGetter.include()) {
            return;
        }
        Class<?> flatClassType = includeParserResult.getIncludeNavigateParams().getFlatClassType();
        Map<Object, Object> flatClassMap = includeParserResult.getFlatClassMap();
        String[] directSelfPropertiesOrPrimary = includeParserResult.getNavigateMetadata().getDirectSelfPropertiesOrPrimary(runtimeContext);
        for (RelationExtraEntity entity : entities) {
            RelationValue selfRelationId = entity.getRelationExtraColumns(directSelfPropertiesOrPrimary);
            if (selfRelationId.isNull()) {
                continue;
            }
            Object targetEntity = directToOneGetter.getIncludeValue(selfRelationId);
            if (targetEntity != null) {
                //如果报错cast那么请确认是否保留了最后一层的集合
                //NavigateFlat 展开最后一层后如果使用了ManyToOne或者OneToOne的对象那么必须保留最后一层集合不可以直接Flat到对象
//                setEntityValue(entity.getEntity(), targetEntity);

                if (flatClassType != null) {//特殊处理
                    Object flatPaddingValue = directToOneGetter.getFlatPaddingValue();
                    flatClassMap.put(flatPaddingValue, targetEntity);
                    setEntityValue(entity.getEntity(), flatPaddingValue);
                } else {
                    setEntityValue(entity.getEntity(), targetEntity);
                }
            }
        }
    }


    @Override
    protected void ManyToOneProcess(List<RelationExtraEntity> includes) {
        String[] selfRelationColumn = getSelfRelationColumn();
        RelationIncludeGetter manyToOneGetter = selfNavigateMetadata.getEntityRelationPropertyProvider().getManyToOneGetter(runtimeContext, selfNavigateMetadata, targetColumnMetadataPropertyNames, includes);
        if (manyToOneGetter == null) {
            throw new EasyQueryInvalidOperationException("Please implement the getManyToOneGetter method first.");
        }

        if (!manyToOneGetter.include()) {
            return;
        }
        Class<?> flatClassType = includeParserResult.getIncludeNavigateParams().getFlatClassType();
        Map<Object, Object> flatClassMap = includeParserResult.getFlatClassMap();
        for (RelationExtraEntity entity : entities) {
            RelationValue relationId = entity.getRelationExtraColumns(selfRelationColumn);
            if (relationId.isNull()) {
                continue;
            }
            Object entityInclude = manyToOneGetter.getIncludeValue(relationId);
            if (entityInclude != null) {
                //如果报错cast那么请确认是否保留了最后一层的集合
                //NavigateFlat 展开最后一层后如果使用了ManyToOne或者OneToOne的对象那么必须保留最后一层集合不可以直接Flat到对象

                if (flatClassType != null) {//特殊处理
                    Object flatPaddingValue = manyToOneGetter.getFlatPaddingValue();
                    flatClassMap.put(flatPaddingValue, entityInclude);
                    setEntityValue(entity.getEntity(), flatPaddingValue);
                } else {
                    setEntityValue(entity.getEntity(), entityInclude);
                }
            }
        }
    }

    @Override
    protected void OneToManyProcess(List<RelationExtraEntity> includes) {
        boolean single = singleEntityToManyProcess(includes);
        if (single) {
            return;
        }

        //获取关联关系列的元信息
        String[] selfRelationColumn = getSelfRelationColumn();
        //entities如果size只有1就不需要后续操作
        RelationIncludeGetter oneToManyGetter = selfNavigateMetadata.getEntityRelationPropertyProvider().getOneToManyGetter(runtimeContext, selfNavigateMetadata, targetColumnMetadataPropertyNames, includes);
        if (oneToManyGetter == null) {
            throw new EasyQueryInvalidOperationException("Please implement the getOneToManyGetter method first.");
        }

        for (RelationExtraEntity entity : entities) {
            RelationValue selfRelationId = entity.getRelationExtraColumns(selfRelationColumn);
            Object targetEntities = oneToManyGetter.getIncludeValue(selfRelationId);
            setEntityValue(entity.getEntity(), targetEntities);
        }
    }

    private boolean singleEntityToManyProcess(List<RelationExtraEntity> includes) {
        if (EasyCollectionUtil.isSingle(entities)) {
            RelationExtraEntity first = EasyCollectionUtil.first(entities);
            Collection<Object> manyCollection = EasyCollectionUtil.createManyCollection(selfNavigateMetadata);
            for (RelationExtraEntity include : includes) {
                manyCollection.add(include.getEntity());
            }
            setEntityValue(first.getEntity(), manyCollection);
            return true;
        }
        return false;
    }

    @Override
    protected void ManyToManyProcess(List<RelationExtraEntity> includes, List<Object> mappingRows) {
        boolean single = singleEntityToManyProcess(includes);
        if (single) {
            return;
        }

        EntityRelationPropertyProvider entityRelationPropertyProvider = selfNavigateMetadata.getEntityRelationPropertyProvider();
        //entities如果size只有1就不需要后续操作
        RelationIncludeGetter manyToManyGetter = entityRelationPropertyProvider.getManyToManyGetter(runtimeContext, selfNavigateMetadata, targetColumnMetadataPropertyNames, includes, mappingRows, includeParserResult.isHasOrder());
        if (manyToManyGetter == null) {
            throw new EasyQueryInvalidOperationException("Please implement the getManyToManyGetter method first.");
        }
        // null
        if (includeParserResult.getMappingClass() == null) {

            String[] selfRelationColumn = getSelfRelationColumn();
            for (RelationExtraEntity entity : entities) {
                RelationValue selfRelationId = entity.getRelationExtraColumns(selfRelationColumn);
                Object targetEntities = manyToManyGetter.getIncludeValue(selfRelationId);
                setEntityValue(entity.getEntity(), targetEntities);
            }
        } else {
            String[] selfRelationColumn = getSelfRelationColumn();
            for (RelationExtraEntity entity : entities) {
                RelationValue selfRelationId = entity.getRelationExtraColumns(selfRelationColumn);
                Object targetEntities = manyToManyGetter.getIncludeValue(selfRelationId);
                setEntityValue(entity.getEntity(), targetEntities);
            }
        }
    }

}
