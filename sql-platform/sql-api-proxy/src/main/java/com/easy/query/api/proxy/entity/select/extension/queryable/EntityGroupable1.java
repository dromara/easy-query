package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;

/**
 * create time 2023/12/4 10:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityGroupable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    /**
     * SQL分组
     *
     * <blockquote><pre>
     * {@code
     * .groupBy(o -> GroupKeys.TABLE1.of(o.content().subString(0, 8))) //GROUP BY subString(content,1,8)具体的索引按各种数据库规则
     *
     * .groupBy(o -> GroupKeys.TABLE1.of(o.id())) // GROUP BY id
     *
     * .groupBy(t ->GroupKeys.TABLE1.of( t.id(),t.title())) // GROUP BY id,title
     *    }
     * </pre></blockquote>
     * @param selectExpression groupBy的选择列表达式
     * @return
     * @param <TRProxy>
     * @param <TR>
     */

    <TRProxy extends ProxyEntity<TRProxy, TR> & SQLGroupByExpression , TR> EntityQueryable<TRProxy, TR> groupBy(SQLFuncExpression1<T1Proxy, SQLFuncExpression1<T1Proxy,TRProxy>> selectExpression);
}
