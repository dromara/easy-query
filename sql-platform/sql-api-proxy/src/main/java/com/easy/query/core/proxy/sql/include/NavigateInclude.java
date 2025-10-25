package com.easy.query.core.proxy.sql.include;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2025/10/25 15:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface NavigateInclude<TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> {

    NavigateInclude<TPropertyProxy, TProperty> where(SQLActionExpression1<TPropertyProxy> filter);

    NavigateInclude<TPropertyProxy, TProperty> limit(long offset, long rows);

     <TRProxy extends ProxyEntity<TRProxy, TR>, TR> NavigateInclude<TPropertyProxy, TProperty> select(SQLFuncExpression1<TPropertyProxy, TRProxy> selector);

}
