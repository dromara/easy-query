package com.easy.query.core.proxy.core.draft.group;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2023/12/23 13:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupKeyFetcher<T extends ProxyEntityAvailable<T, TProxy> & GroupKey, TProxy extends ProxyEntity<TProxy, T>> extends SQLGroupByExpression {

    <TProperty> void fetch(int index, PropTypeColumn<TProperty> column);

    default TableAvailable getTable() {
        return null;
    }

    default String getValue() {
        return null;
    }

    default EntitySQLContext getEntitySQLContext() {
        return null;
    }

    T getGroupKey();

    TProxy getGroupKeyProxy();

    Class<?>[] getDraftPropTypes();
}
