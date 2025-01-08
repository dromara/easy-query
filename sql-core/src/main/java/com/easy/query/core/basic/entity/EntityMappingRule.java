package com.easy.query.core.basic.entity;

import com.easy.query.core.enums.EntityMappingStrategyEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntityAliasSegment;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2025/1/6 17:18
 * 对象映射策略 <a href="http://www.easy-query.com/easy-query-doc/startup/mapping-rule.html">对象映射规则</a> <br/>
 *
 * @author xuejiaming
 * @author link2fun add doc
 * @see EntityMappingStrategyEnum
 */
public interface EntityMappingRule {
    /**
     * 获取class表别名属性
     *
     * @param sourceEntityMetadata
     * @param sourceColumnMetadata
     * @param targetEntityMetadata
     * @return
     */
    ColumnMetadata getColumnMetadataBySourcColumnMetadata(EntityMetadata sourceEntityMetadata, ColumnMetadata sourceColumnMetadata, EntityMetadata targetEntityMetadata);

    /**
     * 获取别名表达式表属性名
     *
     * @param sqlEntityAliasSegment
     * @param aliasTable
     * @return
     */
    String getAnonymousPropertyNameFromSQLSegment(SQLEntityAliasSegment sqlEntityAliasSegment, TableAvailable aliasTable);
}
