package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.proxy.extension.functions.ColumnBooleanFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface BooleanTypeExpression<T> extends ObjectTypeExpression<T>,
        ColumnBooleanFunctionAvailable<T> {
    @Override
    default <TR> BooleanTypeExpression<TR> asAnyType(Class<TR> clazz) {
        ObjectTypeExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
    default BooleanTypeExpression<Boolean> asBoolean() {
        return asAnyType(Boolean.class);
    }
}
