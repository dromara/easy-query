package com.easy.query.core.proxy.extension.functions.type;

import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;

import java.util.function.Function;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ObjectTypeExpression<T> extends ColumnFuncComparableExpression<T>{

    default AnyTypeExpression<T> asAny() {
        Class<?> propertyType = getPropertyType();
        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), this.func(), propertyType);
    }
//    default <TR> AnyTypeExpression<TR> valueConvert(Function<T,TR> converter) {
//        Class<?> propertyType = getPropertyType();
//        return new AnyTypeExpressionImpl<>(this.getCurrentEntitySQLContext(), this.getTable(), this.getValue(), this.func(), propertyType);
//    }
}
