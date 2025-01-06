package com.easy.query.core.basic.entity;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyUtil;

/**
 * create time 2025/1/6 17:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class TryColumnAndPropertyEntityMappingRule implements EntityMappingRule {
    @Override
    public ColumnMetadata getColumnMetadataBySourcColumnMetadata(EntityMetadata sourceEntityMetadata, ColumnMetadata sourceColumnMetadata, EntityMetadata targetEntityMetadata) {
        String sourceColumnName = sourceColumnMetadata.getName();
        ColumnMetadata targetColumnMetadata = targetEntityMetadata.getColumnMetadataOrNull(sourceColumnName);
        if (targetColumnMetadata == null) {
            ColumnMetadata columnMetadata = targetEntityMetadata.getProperty2ColumnMap().get(sourceColumnMetadata.getPropertyName());
            if (columnMetadata != null && !columnMetadata.isValueObject()) {
                targetColumnMetadata = columnMetadata;
            }
        }
        return targetColumnMetadata;
    }

    @Override
    public String getAnonymousPropertyNameFromSQLSegment(SQLEntityAliasSegment sqlEntityAliasSegment, TableAvailable aliasTable) {
        String propertyName = EasyUtil.getAnonymousPropertyNameByAlias(sqlEntityAliasSegment, aliasTable);
        if (propertyName == null) {
            return EasyUtil.getAnonymousPropertyNameByProperty(sqlEntityAliasSegment, aliasTable);
        }
        return propertyName;
    }
}
