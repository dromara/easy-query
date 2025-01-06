package com.easy.query.core.basic.entity;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyUtil;

/**
 * create time 2025/1/6 19:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyEntityMappingRule implements EntityMappingRule{
    @Override
    public ColumnMetadata getColumnMetadataBySourcColumnMetadata(EntityMetadata sourceEntityMetadata, ColumnMetadata sourceColumnMetadata, EntityMetadata targetEntityMetadata) {

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
