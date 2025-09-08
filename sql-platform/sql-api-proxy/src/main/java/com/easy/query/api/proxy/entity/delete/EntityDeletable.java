package com.easy.query.api.proxy.entity.delete;

import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.SQLBatchExecute;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectExpression;

import java.util.List;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * create time 2023/3/6 13:07
 */
public interface EntityDeletable<TProxy extends ProxyEntity<TProxy, T>, T> extends Deletable<T, EntityDeletable<TProxy,T>>
        , ConfigureVersionable<EntityDeletable<TProxy,T>>
        , SQLBatchExecute<EntityDeletable<TProxy,T>> {
    List<T> getEntities();
    TProxy getTProxy();

    ClientEntityDeletable<T> getClientDelete();
    default EntityDeletable<TProxy, T> whereColumns(SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        return whereColumns(true, columnSelectorExpression);
    }

    default EntityDeletable<TProxy, T> whereColumns(boolean condition, SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        getClientDelete().whereColumns(condition, selector -> {
            SQLSelectExpression sqlSelectExpression = columnSelectorExpression.apply(getTProxy());
            sqlSelectExpression.accept(selector.getOnlySelector());
        });
        return this;
    }
}
