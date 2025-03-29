package com.easy.query.core.expression.include;

import com.easy.query.core.expression.sql.include.RelationExtraEntity;

import java.util.List;

/**
 * create time 2025/3/29 20:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropertyIncludeProcessor {
    void OneToOneProcess(List<RelationExtraEntity> includes);
    void DirectToOneProcess(List<RelationExtraEntity> includes);
    void ManyToOneProcess(List<RelationExtraEntity> includes);
    void OneToManyProcess(List<RelationExtraEntity> includes);
    void ManyToManyProcess(List<RelationExtraEntity> includes, List<Object> mappingRows);
    <T> void setEntityValue(T entity, Object value);
}
