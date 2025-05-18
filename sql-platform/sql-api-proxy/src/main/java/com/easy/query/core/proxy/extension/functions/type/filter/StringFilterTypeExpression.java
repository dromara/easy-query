package com.easy.query.core.proxy.extension.functions.type.filter;

import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface StringFilterTypeExpression<T> extends StringTypeExpression<T> {
    default StringTypeExpression<T> filter(SQLActionExpression predicate) {
        this._toFilter(predicate);
        return this;
    }

    @Override
    default <TR> StringFilterTypeExpression<TR> asAnyType(Class<TR> clazz) {
        StringTypeExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
    void _toFilter(SQLActionExpression predicate);

}
