package com.easy.query.core.proxy.extra;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.extra.ExtraConfigure;
import com.easy.query.core.expression.parser.core.extra.ExtraFilter;
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
    private ExtraFilter extraFilter;
    private ExtraConfigure extraConfigure;
    private ExtraSelect extraSelect;

    @Override
    public EntityExtraAutoIncludeConfigure<TProxy, TEntity> configure(SQLExpression1<EntityQueryable<TProxy,TEntity>> queryableConfigureExpression) {
        this.extraConfigure = new EntityExtraConfigure<>(queryableConfigureExpression);
        return this;
    }

    @Override
    public EntityExtraAutoIncludeConfigure<TProxy, TEntity> filter(SQLExpression1<TProxy> whereExpression) {
        this.extraFilter = new EntityExtraFilter<>(whereExpression);
        return this;
    }

    @Override
    public EntityExtraAutoIncludeConfigure<TProxy, TEntity> select(SQLFuncExpression1<TProxy, SQLSelectAsExpression> selectExpression) {
        this.extraSelect = new EntityExtraSelect<>(selectExpression);
        return this;
    }

    @Override
    public ExtraFilter getExtraFilter() {
        return extraFilter;
    }

    @Override
    public ExtraSelect getExtraSelect() {
        return extraSelect;
    }

    @Override
    public ExtraConfigure getExtraConfigure() {
        return extraConfigure;
    }
}
