package com.easy.query.core.basic.entity;

import com.easy.query.core.enums.EntityMappingStrategyEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyUtil;

/**
 * create time 2025/1/6 19:49
 * 对象映射规则<br/>
 * 默认策略:表示实体的对应的列名和映射对象的列名相同能映射
 *
 * @author xuejiaming
 * @author link2fun add doc
 * @see EntityMappingStrategyEnum#COLUMN_ONLY
 */
public class ColumnEntityMappingRule implements EntityMappingRule {
    @Override
    public ColumnMetadata getColumnMetadataBySourcColumnMetadata(EntityMetadata sourceEntityMetadata, ColumnMetadata sourceColumnMetadata, EntityMetadata targetEntityMetadata) {
        String sourceColumnName = sourceColumnMetadata.getName();
        return targetEntityMetadata.getColumnMetadataOrNull(sourceColumnName);
    }

    @Override
    public String getAnonymousPropertyNameFromSQLSegment(SQLEntityAliasSegment sqlEntityAliasSegment, TableAvailable aliasTable) {
        return EasyUtil.getAnonymousPropertyNameByAlias(sqlEntityAliasSegment, aliasTable);
    }
}
