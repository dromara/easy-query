package com.easy.query.core.expression.sql.include;

/**
 * create time 2024/4/13 13:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RelationExtraEntity {
    Object getEntity();

    RelationValue getRelationExtraColumns(String[] propertyNames);
}
