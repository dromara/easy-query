package com.easy.query.core.proxy.extra;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.extra.ExtraConfigure;
import com.easy.query.core.expression.parser.core.extra.ExtraWhere;
import com.easy.query.core.expression.parser.core.extra.ExtraSelect;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;

/**
 * create time 2025/4/16 22:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityExtraAutoIncludeConfigureImpl<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements EntityExtraAutoIncludeConfigure<TProxy, TEntity> {
    private ExtraWhere extraWhere;
    private ExtraConfigure extraConfigure;
    private ExtraSelect extraSelect;
    private boolean ignoreNavigateConfigure;

    @Override
    public EntityExtraAutoIncludeConfigure<TProxy, TEntity> configure(SQLActionExpression1<EntityQueryable<TProxy, TEntity>> queryableConfigureExpression) {
        this.extraConfigure = new EntityExtraConfigure<>(queryableConfigureExpression);
        return this;
    }

    @Override
    public EntityExtraAutoIncludeConfigure<TProxy, TEntity> ignoreNavigateConfigure() {
        this.ignoreNavigateConfigure = true;
        return this;
    }

    @Override
    public EntityExtraAutoIncludeConfigure<TProxy, TEntity> where(SQLActionExpression1<TProxy> whereExpression) {
        this.extraWhere = new EntityExtraWhere<>(whereExpression);
        return this;
    }

    @Override
    public EntityExtraAutoIncludeConfigure<TProxy, TEntity> select(SQLFuncExpression1<TProxy, SQLSelectAsExpression> selectExpression) {
        this.extraSelect = new EntityExtraSelect<>(selectExpression);
        return this;
    }

    @Override
    public ExtraWhere getExtraWhere() {
        return extraWhere;
    }

    @Override
    public ExtraSelect getExtraSelect() {
        return extraSelect;
    }

    @Override
    public ExtraConfigure getExtraConfigure() {
        return extraConfigure;
    }

    @Override
    public boolean isIgnoreNavigateConfigure() {
        return ignoreNavigateConfigure;
    }
}
