package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.impl.SQLNavigateIncludeImpl;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

/**
 * create time 2023/8/17 13:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLInclude1Extension<T1> extends ClientQueryableAvailable<T1>, QueryableAvailable<T1> {

    default <TProperty> Queryable<T1> include(SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TProperty>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    default <TProperty> Queryable<T1> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TProperty>> navigateIncludeSQLExpression) {
        if (condition) {
            getClientQueryable().<TProperty>include(navigateInclude -> navigateIncludeSQLExpression.apply(new SQLNavigateIncludeImpl<>(navigateInclude)).getClientQueryable());
        }
        return getQueryable();
    }
}
