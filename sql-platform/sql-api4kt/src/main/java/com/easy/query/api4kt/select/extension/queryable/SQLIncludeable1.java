package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.sql.SQLKtNavigateInclude;
import com.easy.query.api4kt.sql.impl.SQLKtNavigateIncludeImpl;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

/**
 * create time 2023/8/17 13:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLIncludeable1<T1> extends ClientKtQueryableAvailable<T1>, KtQueryableAvailable<T1> {

    default <TProperty> KtQueryable<T1> include(SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TProperty>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    default <TProperty> KtQueryable<T1> include(boolean condition, SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TProperty>> navigateIncludeSQLExpression) {
        if (condition) {
            getClientQueryable().<TProperty>include(navigateInclude -> navigateIncludeSQLExpression.apply(new SQLKtNavigateIncludeImpl<>(navigateInclude)).getClientQueryable());
        }
        return getQueryable();
    }
}
