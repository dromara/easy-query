package com.easy.query.core.proxy.core.draft.proxy;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.ValueObjectProxyEntity;

/**
 * create time 2024/1/26 22:02
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDraftProxy<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractProxyEntity<TProxy,TEntity> implements DraftProxy {
    private final Class<?>[] propTypes;
    public AbstractDraftProxy(int capacity){
        this.propTypes=new Class[capacity];
    }
    @Override
    public <TProperty> void fetch(int index, PropTypeColumn<TProperty> column, TablePropColumn tablePropColumn) {
        SQLSelectAsExpression sqlSelectAsExpression = column.as(tablePropColumn);
        if (sqlSelectAsExpression instanceof ValueObjectProxyEntity) {
            throw new EasyQueryInvalidOperationException("draft result not support value object columns");
        }
        propTypes[index]=column.getPropertyType();
        selectExpression(sqlSelectAsExpression);
    }

    @Override
    public Class<?>[] getDraftPropTypes() {
        return propTypes;
    }
}
