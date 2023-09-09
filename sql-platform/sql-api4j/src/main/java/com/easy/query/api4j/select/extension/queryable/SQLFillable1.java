package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.sql.SQLFillSelector;
import com.easy.query.api4j.sql.impl.SQLFillSelectorImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * create time 2023/8/17 13:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFillable1<T1> extends ClientQueryableAvailable<T1>, QueryableAvailable<T1> {


    default <TREntity> Queryable<T1> fillMany(SQLFuncExpression1<SQLFillSelector, Queryable<TREntity>> fillSetterExpression, Property<TREntity, ?> targetProperty,Property<T1, ?> selfProperty,  BiConsumer<T1, Collection<TREntity>> produce) {
        getClientQueryable().fillMany(fillSelector -> {
            return fillSetterExpression.apply(new SQLFillSelectorImpl(fillSelector)).getClientQueryable();
        }, EasyLambdaUtil.getPropertyName(targetProperty), selfProperty, produce);
        return getQueryable();
    }
    default <TREntity> Queryable<T1> fillOne(SQLFuncExpression1<SQLFillSelector, Queryable<TREntity>> fillSetterExpression, Property<TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, TREntity> produce) {
        getClientQueryable().fillOne(fillSelector -> {
            return fillSetterExpression.apply(new SQLFillSelectorImpl(fillSelector)).getClientQueryable();
        }, EasyLambdaUtil.getPropertyName(targetProperty), selfProperty, produce);
        return getQueryable();
    }
}
