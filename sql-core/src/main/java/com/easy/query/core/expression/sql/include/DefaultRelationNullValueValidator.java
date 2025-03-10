package com.easy.query.core.expression.sql.include;

import com.easy.query.core.util.EasyStringUtil;

import java.util.Objects;

/**
 * create time 2025/3/10 12:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRelationNullValueValidator implements RelationNullValueValidator {
    @Override
    public boolean isNullValue(Object value) {
        if (Objects.isNull(value)) {
            return true;
        }
        if (value instanceof String) {
            if (EasyStringUtil.isBlank((String) value)) {
                return true;
            }
        }
        return false;
    }
}
