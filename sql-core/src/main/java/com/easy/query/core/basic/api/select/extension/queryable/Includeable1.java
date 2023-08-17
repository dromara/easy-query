package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;

/**
 * create time 2023/8/17 13:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Includeable1<T1> {

    default <TREntity> ClientQueryable<T1> include(SQLFuncExpression1<NavigateInclude<T1>, ClientQueryable<TREntity>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    <TREntity> ClientQueryable<T1> include(boolean condition, SQLFuncExpression1<NavigateInclude<T1>, ClientQueryable<TREntity>> navigateIncludeSQLExpression);

}
