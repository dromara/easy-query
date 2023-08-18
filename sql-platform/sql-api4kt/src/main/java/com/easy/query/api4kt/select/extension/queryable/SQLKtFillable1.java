package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.sql.SQLKtFillSelector;
import com.easy.query.api4kt.sql.impl.SQLKtFillSelectorImpl;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import kotlin.reflect.KProperty1;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * create time 2023/8/17 13:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtFillable1<T1> extends ClientKtQueryableAvailable<T1>, KtQueryableAvailable<T1> {


    default <TREntity> KtQueryable<T1> fillMany(SQLFuncExpression1<SQLKtFillSelector, KtQueryable<TREntity>> fillSetterExpression, KProperty1<TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
        return fillMany(true, fillSetterExpression, targetProperty, selfProperty, produce, false);
    }

    default <TREntity> KtQueryable<T1> fillMany(boolean condition, SQLFuncExpression1<SQLKtFillSelector, KtQueryable<TREntity>> fillSetterExpression, KProperty1<TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
        return fillMany(condition, fillSetterExpression, targetProperty, selfProperty, produce, false);
    }

    default <TREntity> KtQueryable<T1> fillMany(SQLFuncExpression1<SQLKtFillSelector, KtQueryable<TREntity>> fillSetterExpression, KProperty1<TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, Collection<TREntity>> produce, boolean consumeNull) {
        return fillMany(true, fillSetterExpression, targetProperty, selfProperty, produce, consumeNull);
    }

    default <TREntity> KtQueryable<T1> fillMany(boolean condition, SQLFuncExpression1<SQLKtFillSelector, KtQueryable<TREntity>> fillSetterExpression, KProperty1<TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, Collection<TREntity>> produce, boolean consumeNull) {
        if (condition) {
            getClientQueryable().fillMany(true, fillSelector -> {
                return fillSetterExpression.apply(new SQLKtFillSelectorImpl(fillSelector)).getClientQueryable();
            }, EasyKtLambdaUtil.getPropertyName(targetProperty), selfProperty, produce, consumeNull);
        }
        return getQueryable();
    }

    default <TREntity> KtQueryable<T1> fillOne(SQLFuncExpression1<SQLKtFillSelector, KtQueryable<TREntity>> fillSetterExpression, KProperty1<TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, TREntity> produce) {
        return fillOne(true, fillSetterExpression, targetProperty, selfProperty, produce);
    }

    default <TREntity> KtQueryable<T1> fillOne(boolean condition, SQLFuncExpression1<SQLKtFillSelector, KtQueryable<TREntity>> fillSetterExpression, KProperty1<TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, TREntity> produce) {
        return fillOne(condition, fillSetterExpression, targetProperty, selfProperty, produce, false);
    }

    default <TREntity> KtQueryable<T1> fillOne(SQLFuncExpression1<SQLKtFillSelector, KtQueryable<TREntity>> fillSetterExpression, KProperty1<TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, TREntity> produce, boolean consumeNull) {
        return fillOne(true, fillSetterExpression, targetProperty, selfProperty, produce, consumeNull);
    }

    default <TREntity> KtQueryable<T1> fillOne(boolean condition, SQLFuncExpression1<SQLKtFillSelector, KtQueryable<TREntity>> fillSetterExpression, KProperty1<TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, TREntity> produce, boolean consumeNull) {
        if (condition) {
            getClientQueryable().fillOne(true, fillSelector -> {
                return fillSetterExpression.apply(new SQLKtFillSelectorImpl(fillSelector)).getClientQueryable();
            }, EasyKtLambdaUtil.getPropertyName(targetProperty), selfProperty, produce, consumeNull);
        }
        return getQueryable();
    }
}
