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


    default <TREntity> KtQueryable<T1> fillMany(SQLFuncExpression1<SQLKtFillSelector, KtQueryable<TREntity>> fillSetterExpression, KProperty1<? super TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
        getClientQueryable().fillMany(fillSelector -> {
            return fillSetterExpression.apply(new SQLKtFillSelectorImpl(fillSelector)).getClientQueryable();
        }, EasyKtLambdaUtil.getPropertyName(targetProperty), selfProperty, produce);
        return getQueryable();
    }
    default <TREntity> KtQueryable<T1> fillOne(SQLFuncExpression1<SQLKtFillSelector, KtQueryable<TREntity>> fillSetterExpression, KProperty1<? super TREntity, ?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, TREntity> produce) {
        getClientQueryable().fillOne(fillSelector -> {
            return fillSetterExpression.apply(new SQLKtFillSelectorImpl(fillSelector)).getClientQueryable();
        }, EasyKtLambdaUtil.getPropertyName(targetProperty), selfProperty, produce);
        return getQueryable();
    }
}
