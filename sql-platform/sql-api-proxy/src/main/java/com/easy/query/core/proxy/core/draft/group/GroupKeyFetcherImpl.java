package com.easy.query.core.proxy.core.draft.group;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.ValueObjectProxyEntity;

/**
 * create time 2023/12/19 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupKeyFetcherImpl<T extends ProxyEntityAvailable<T, TProxy> & GroupKey, TProxy extends ProxyEntity<TProxy, T>> implements GroupKeyFetcher<T,TProxy> {
    private final T groupKey;
    private final TProxy draftProxy;
    private SQLGroupByExpression sqlGroupByExpression = SQLGroupByExpression.empty;
    private final Class<?>[] propTypes;

    public GroupKeyFetcherImpl(T groupKey, TProxy draftProxy) {

        this.groupKey = groupKey;
        this.draftProxy = draftProxy;
        this.propTypes=new Class<?>[groupKey.capacity()];
    }

    @Override
    public <TProperty> void fetch(int index,PropTypeColumn<TProperty> column) {
        if (column instanceof ValueObjectProxyEntity) {
            throw new EasyQueryInvalidOperationException("draft result not support value object columns");
        }
        propTypes[index]=column.propertyType();
        this.sqlGroupByExpression = this.sqlGroupByExpression._thenBy(column);
    }

    @Override
    public void accept(GroupSelector s) {
        sqlGroupByExpression.accept(s);
    }


    @Override
    public T getGroupKey() {
        return groupKey;
    }

    @Override
    public TProxy getGroupKeyProxy() {
        return draftProxy;
    }

    @Override
    public Class<?>[] getDraftPropTypes() {
        return propTypes;
    }
}
