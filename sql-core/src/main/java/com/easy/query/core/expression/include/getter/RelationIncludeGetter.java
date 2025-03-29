package com.easy.query.core.expression.include.getter;

import com.easy.query.core.expression.sql.include.RelationValue;

/**
 * create time 2025/3/29 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RelationIncludeGetter {
    boolean include();
    Object getIncludeValue(RelationValue relationValue);
}
