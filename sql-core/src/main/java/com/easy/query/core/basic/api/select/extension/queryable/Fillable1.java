package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.FillSelector;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * create time 2023/8/17 13:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Fillable1<T1> {
    <TREntity> ClientQueryable<T1> fillMany(SQLFuncExpression1<FillSelector, ClientQueryable<TREntity>> fillSetterExpression, String targetProperty, Property<T1,?> selfProperty, BiConsumer<T1, Collection<TREntity>> produce);

    <TREntity> ClientQueryable<T1> fillOne(SQLFuncExpression1<FillSelector, ClientQueryable<TREntity>> fillSetterExpression, String targetProperty, Property<T1,?> selfProperty, BiConsumer<T1, TREntity> produce);

}
