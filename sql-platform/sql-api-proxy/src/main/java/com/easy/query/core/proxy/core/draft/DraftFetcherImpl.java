package com.easy.query.core.proxy.core.draft;

import com.easy.query.core.basic.jdbc.executor.internal.enumerable.Draft;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.ValueObjectProxyEntity;

/**
 * create time 2023/12/19 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class DraftFetcherImpl<T extends ProxyEntityAvailable<T, TProxy> & Draft, TProxy extends ProxyEntity<TProxy, T>> implements DraftFetcher<T,TProxy> {
    private final T draft;
    private final TProxy draftProxy;
    private SQLSelectAsExpression sqlSelectAsExpression = SQLSelectAsExpression.empty;
    private final Class<?>[] propTypes;

    public DraftFetcherImpl(T draft,TProxy draftProxy) {

        this.draft = draft;
        this.draftProxy = draftProxy;
        this.propTypes=new Class<?>[draft.capacity()];
    }

    @Override
    public <TProperty> void fetch(int index,PropTypeColumn<TProperty> column, TablePropColumn tablePropColumn) {
        SQLSelectAsExpression sqlSelectAsExpression = column.as(tablePropColumn);
        if (sqlSelectAsExpression instanceof ValueObjectProxyEntity) {
            throw new EasyQueryInvalidOperationException("draft result not support value object columns");
        }
        propTypes[index]=column.getPropertyType();
        this.sqlSelectAsExpression = this.sqlSelectAsExpression._concat(sqlSelectAsExpression);
    }

    @Override
    public void accept(GroupSelector s) {
        sqlSelectAsExpression.accept(s);
    }

    @Override
    public void accept(AsSelector s) {
        sqlSelectAsExpression.accept(s);
    }

    @Override
    public void accept(Selector s) {
        sqlSelectAsExpression.accept(s);
    }

    @Override
    public T getDraft() {
        return draft;
    }

    @Override
    public TProxy getDraftProxy() {
        return draftProxy;
    }

    @Override
    public Class<?>[] getDraftPropTypes() {
        return propTypes;
    }
}
