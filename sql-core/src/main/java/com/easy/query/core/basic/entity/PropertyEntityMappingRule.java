package com.easy.query.core.basic.entity;

import com.easy.query.core.enums.EntityMappingStrategyEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyUtil;

/**
 * create time 2025/1/6 19:49<br/>
 * 对象映射规则: 表示实体的对应的属性名和映射对象的属性名相同能映射 <br/>
 *
 * @author xuejiaming
 * @author link2fun add doc
 * @see EntityMappingStrategyEnum#PROPERTY_ONLY
 */
public class PropertyEntityMappingRule implements EntityMappingRule {
    @Override
    public ColumnMetadata getColumnMetadataBySourcColumnMetadata(EntityMetadata sourceEntityMetadata, ColumnMetadata sourceColumnMetadata, EntityMetadata targetEntityMetadata) {
        if (sourceColumnMetadata.getPropertyName() == null) {
            return null;
        }
        ColumnMetadata columnMetadata = targetEntityMetadata.getProperty2ColumnMap().get(sourceColumnMetadata.getPropertyName());
        if (columnMetadata != null && !columnMetadata.isValueObject()) {
            return columnMetadata;
        }
        return null;
    }

    @Override
    public String getAnonymousPropertyNameFromSQLSegment(SQLEntityAliasSegment sqlEntityAliasSegment, TableAvailable aliasTable) {
        return EasyUtil.getAnonymousPropertyNameByProperty(sqlEntityAliasSegment, aliasTable);
    }
}
