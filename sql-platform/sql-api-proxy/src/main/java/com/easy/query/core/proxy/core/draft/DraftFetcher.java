package com.easy.query.core.proxy.core.draft;

import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DraftResult;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2023/12/18 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DraftFetcher<T extends ProxyEntityAvailable<T, TProxy> & DraftResult, TProxy extends ProxyEntity<TProxy, T>> extends SQLSelectAsExpression {

    <TProperty> void fetch(int index,PropTypeColumn<TProperty> column, TablePropColumn tablePropColumn);

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
