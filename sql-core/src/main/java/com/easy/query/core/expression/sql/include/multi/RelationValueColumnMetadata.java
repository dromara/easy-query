package com.easy.query.core.expression.sql.include.multi;

import com.easy.query.core.expression.sql.include.RelationValue;

import java.util.Map;

/**
 * create time 2024/10/17 08:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RelationValueColumnMetadata {
    RelationValue getRelationValue(Object entity);

    RelationValue getRelationValue(Map<String, Object> mappingRow);

    RelationValue getName();
}
