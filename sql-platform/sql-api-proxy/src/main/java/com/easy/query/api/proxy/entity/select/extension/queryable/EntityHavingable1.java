package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityHavingable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {



    default EntityQueryable<T1Proxy, T1> having(SQLExpression1<T1Proxy> aggregateFilterSQLExpression) {
        return having(true, aggregateFilterSQLExpression);
    }

    EntityQueryable<T1Proxy, T1> having(boolean condition, SQLExpression1<T1Proxy> aggregateFilterSQLExpression);


}
