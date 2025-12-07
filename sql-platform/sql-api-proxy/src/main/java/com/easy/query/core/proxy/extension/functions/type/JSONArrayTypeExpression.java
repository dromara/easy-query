package com.easy.query.core.proxy.extension.functions.type;

import com.easy.query.core.proxy.extension.functions.ColumnJSONArrayFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.ColumnJSONObjectFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface JSONArrayTypeExpression<T> extends ObjectTypeExpression<T>,
        ColumnJSONArrayFunctionAvailable<T> {
    @Override
    default <TR> JSONArrayTypeExpression<TR> asAnyType(Class<TR> clazz) {
        ObjectTypeExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
}
