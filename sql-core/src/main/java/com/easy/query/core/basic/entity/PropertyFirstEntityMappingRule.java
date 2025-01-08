package com.easy.query.core.basic.entity;

import com.easy.query.core.enums.EntityMappingStrategyEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyUtil;

/**
 * create time 2025/1/6 19:49 <br/>
 * 对象映射规则:  表示实体的对应的属性名和映射对象的属性名相同能映射,和PROPERTY_ONLY的区别就是如果是函数式片段没有property通过alias来匹配<br/>
 * 如果你无法理解那么请在新项目的时候选择 PROPERTY_FIRST❗️<br/>
 * 如果你无法理解那么请在新项目的时候选择 PROPERTY_FIRST❗️<br/>
 * 如果你无法理解那么请在新项目的时候选择 PROPERTY_FIRST❗️<br/>
 *
 * @author xuejiaming
 * @author link2fun add doc
 * @see EntityMappingStrategyEnum#PROPERTY_FIRST
 */
public class PropertyFirstEntityMappingRule implements EntityMappingRule {
    @Override
    public ColumnMetadata getColumnMetadataBySourcColumnMetadata(EntityMetadata sourceEntityMetadata, ColumnMetadata sourceColumnMetadata, EntityMetadata targetEntityMetadata) {

        if (sourceColumnMetadata.getPropertyName() == null) {
            String sourceColumnName = sourceColumnMetadata.getName();
            return targetEntityMetadata.getColumnMetadataOrNull(sourceColumnName);
        }
        ColumnMetadata columnMetadata = targetEntityMetadata.getProperty2ColumnMap().get(sourceColumnMetadata.getPropertyName());
        if (columnMetadata != null && !columnMetadata.isValueObject()) {
            return columnMetadata;
        }
        return null;
    }

    @Override
    public String getAnonymousPropertyNameFromSQLSegment(SQLEntityAliasSegment sqlEntityAliasSegment, TableAvailable aliasTable) {
        return EasyUtil.getAnonymousPropertyNameByPropertyFirst(sqlEntityAliasSegment, aliasTable);
    }
}
