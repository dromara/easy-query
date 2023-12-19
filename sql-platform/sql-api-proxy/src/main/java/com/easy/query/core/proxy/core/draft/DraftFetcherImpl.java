package com.easy.query.core.proxy.core.draft;

import com.easy.query.core.basic.jdbc.executor.internal.enumerable.Draft;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.ValueObjectProxyEntity;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/12/19 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class DraftFetcherImpl<T extends ProxyEntityAvailable<T, TProxy> & Draft, TProxy extends ProxyEntity<TProxy, T>> implements DraftFetcher<T,TProxy> {
    private final T draft;
    private final TProxy draftProxy;
    private SQLSelectAsExpression sqlSelectExpression = SQLSelectAsExpression.empty;
    private final Class<?>[] propTypes;

    public DraftFetcherImpl(T draft,TProxy draftProxy) {

        this.draft = draft;
        this.draftProxy = draftProxy;
        this.propTypes=new Class<?>[draft.capacity()];
    }

    @Override
    public void fetch(SQLSelectAsExpression... selectAsExpressions) {
        if (EasyArrayUtil.isNotEmpty(selectAsExpressions)) {
            for (SQLSelectAsExpression selectAsExpression : selectAsExpressions) {
                if (selectAsExpression instanceof ValueObjectProxyEntity) {
                    throw new EasyQueryInvalidOperationException("draft result not support value object columns");
                }
                sqlSelectExpression = sqlSelectExpression._concat(selectAsExpression);
            }
        }
    }

    @Override
    public void fetch(SQLSelectExpression... selectExpressions) {
        if (EasyArrayUtil.isNotEmpty(selectExpressions)) {
            for (SQLSelectExpression selectExpression : selectExpressions) {
                if (selectExpression instanceof ValueObjectProxyEntity) {
                    throw new EasyQueryInvalidOperationException("draft result not support value object columns");
                }
                sqlSelectExpression = new SQLSelectAsImpl(x -> {
                    accept(x);
                    selectExpression.accept(x);
                }, x -> {
                    accept(x);
                    selectExpression.accept(x);
                }, x -> {
                    throw new UnsupportedOperationException();
                });
            }
        }
    }

    @Override
    public void accept(GroupSelector s) {
        sqlSelectExpression.accept(s);
    }

    @Override
    public void accept(AsSelector s) {
        sqlSelectExpression.accept(s);
    }

    @Override
    public void accept(Selector s) {
        sqlSelectExpression.accept(s);
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
