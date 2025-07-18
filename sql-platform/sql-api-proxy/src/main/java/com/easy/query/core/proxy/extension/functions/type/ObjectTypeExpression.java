package com.easy.query.core.proxy.extension.functions.type;

import com.easy.query.core.expression.lambda.ValueConvertFunction;
import com.easy.query.core.proxy.PropValueConvertColumn;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.proxy.impl.PropValueConvertColumnImpl;

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

    /**
     * t.createTime().format("yyyy-MM").valueConvert(d -> d+"Month")
     * 内存转换
     * @param converter 内存转换表达式
     * @return 返回一个呗转换后的列表达式
     * @param <TR> 发挥类型
     */
    default <TR> PropValueConvertColumn<TR> valueConvert(ValueConvertFunction<T,TR> converter) {
        return new PropValueConvertColumnImpl<>(this,converter);
    }
}
