package com.easy.query.core.proxy.extra;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.extra.ExtraConfigure;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2025/4/16 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityExtraConfigure<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ExtraConfigure {

    private final SQLExpression1<EntityQueryable<TProxy, TEntity>> queryableConfigureExpression;

    public EntityExtraConfigure(SQLExpression1<EntityQueryable<TProxy, TEntity>> queryableConfigureExpression) {
        this.queryableConfigureExpression = queryableConfigureExpression;
    }

    @Override
    public void configure(ClientQueryable<?> clientQueryable) {
        ProxyEntity tEntityProxy = EntityQueryProxyManager.create(EasyObjectUtil.typeCastNullable(clientQueryable.queryClass()));

        EasyEntityQueryable<TProxy, TEntity> entityQueryable = new EasyEntityQueryable<>(EasyObjectUtil.typeCastNullable(tEntityProxy), EasyObjectUtil.typeCastNullable(clientQueryable));
        queryableConfigureExpression.apply(entityQueryable);

    }
}
