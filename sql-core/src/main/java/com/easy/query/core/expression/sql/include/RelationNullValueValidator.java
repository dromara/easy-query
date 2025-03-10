package com.easy.query.core.expression.sql.include;

/**
 * create time 2025/3/10 12:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RelationNullValueValidator {
    boolean isNullValue(Object value);

    default <TValue> TValue getRelationValueOrNull(TValue value) {
        if (isNullValue(value)) {
            return null;
        }
        return value;
    }
}
