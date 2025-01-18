package com.easy.query.core.expression.sql.include;

import com.easy.query.core.basic.extension.navigate.NavigateValueSetter;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateFlatMetadata;

import java.util.List;
import java.util.Map;

/**
 * create time 2023/7/21 10:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncludeParserResult {
    EntityMetadata getEntityMetadata();

    RelationTypeEnum getRelationType();

    /**
     * 主表导航属性名称
     */
    String getNavigatePropertyName();

    /**
     * 主表导航属性的原始类型
     *
     * @return
     */
    Class<?> getNavigateOriginalPropertyType();

    /**
     * 主表导航属性内部类型
     */
    Class<?> getNavigatePropertyType();

    String[] getSelfProperties();

    String[] getTargetProperties();
    Class<?> getMappingClass();

    String[] getSelfMappingProperties();

    String[] getTargetMappingProperties();

    List<RelationExtraEntity> getIncludeResult();
    List<Map<String, Object>> getMappingRows();

    PropertySetterCaller<Object> getSetter();
    Property<Object,?> getGetter();
    List<RelationExtraEntity> getRelationExtraEntities();
    List<NavigateFlatMetadata> getNavigateFlatMetadataList();
    EntityMetadata getFlatQueryEntityMetadata();

}
