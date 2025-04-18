package com.easy.query.test.common;

import com.easy.query.core.expression.sql.include.DefaultRelationNullValueValidator;
import com.easy.query.core.expression.sql.include.RelationNullValueValidator;
import com.easy.query.core.util.EasyStringUtil;

import java.util.Objects;

/**
 * create time 2025/4/17 20:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyRelationNullValueValidator implements RelationNullValueValidator {
    @Override
    public boolean isNullValue(Object value) {
        if (Objects.isNull(value)) {
            return true;
        }
        if (value instanceof String) {
            if (EasyStringUtil.isBlank((String) value)) {
                return true;
            }
            if (Objects.equals("-", value) || Objects.equals("/", value)) {
                return true;
            }
        }
        return false;
    }
}
