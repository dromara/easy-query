package com.easy.query.core.proxy.core.draft;

import com.easy.query.core.basic.jdbc.executor.internal.enumerable.Draft;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2023/12/18 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DraftFetcher<T extends ProxyEntityAvailable<T, TProxy> & Draft, TProxy extends ProxyEntity<TProxy, T>> extends SQLSelectAsExpression{
    void fetch(SQLSelectAsExpression... selectAsExpressions);
    void fetch(SQLSelectExpression... selectExpressions);
    default TableAvailable getTable() {
        return null;
    }

    default String getValue() {
        return null;
    }

    default EntitySQLContext getEntitySQLContext() {
        return null;
    }

     T getDraft();
    TProxy getDraftProxy();
    Class<?>[] getDraftPropTypes();
}
