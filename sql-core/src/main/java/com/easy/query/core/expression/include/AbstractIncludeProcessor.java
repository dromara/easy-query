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
import com.easy.query.core.metadata.NavigateMetadata;
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
    protected final NavigateMetadata selfNavigateMetadata;
    protected final IncludeParserResult includeParserResult;
    protected final QueryRuntimeContext runtimeContext;
    protected final RelationValueColumnMetadataFactory relationValueColumnMetadataFactory;
    protected final EntityMetadata targetEntityMetadata;
    protected final String[] targetColumnMetadataPropertyNames;

    public AbstractIncludeProcessor(IncludeParserResult includeParserResult, QueryRuntimeContext runtimeContext) {
        this.entities = includeParserResult.getRelationExtraEntities();
        this.includeParserResult = includeParserResult;
        this.selfEntityMetadata = includeParserResult.getEntityMetadata();
        this.selfNavigateMetadata = includeParserResult.getNavigateMetadata();
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
