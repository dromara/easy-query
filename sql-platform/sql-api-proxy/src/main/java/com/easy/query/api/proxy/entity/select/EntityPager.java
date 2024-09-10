package com.easy.query.api.proxy.entity.select;

import com.easy.query.api.proxy.entity.select.extension.queryable.EntityPageAble1;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/9/19 21:57
 * 自定义分页器
 *
 * @author xuejiaming
 */
public interface EntityPager<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,TPageResult> {
    /**
     * 返回分页结果
     * @param query 分页表达式
     * @return 返回自定义分页结果
     */
    <TR> TPageResult toResult(SQLFuncExpression1<EntityQueryable<T1Proxy, T1>, Query<TR>> selectExpression, EntityPageAble1<T1Proxy,T1> query);

}
