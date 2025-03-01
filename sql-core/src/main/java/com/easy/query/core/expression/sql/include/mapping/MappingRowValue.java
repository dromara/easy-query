package com.easy.query.core.expression.sql.include.mapping;

import com.easy.query.core.expression.sql.include.RelationValue;

/**
 * create time 2025/2/28 21:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MappingRowValue {
    RelationValue getTargetValue(String selfProp);
}
