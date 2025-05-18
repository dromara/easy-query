package com.easy.query.core.proxy.extension.functions.type.filter;

import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface BooleanFilterTypeExpression<T> extends BooleanTypeExpression<T> {
    default BooleanTypeExpression<T> filter(SQLActionExpression predicate) {
        this._toFilter(predicate);
        return this;
    }

    @Override
    default <TR> BooleanFilterTypeExpression<TR> asAnyType(Class<TR> clazz) {
        BooleanTypeExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
    void _toFilter(SQLActionExpression predicate);

}
