package com.easy.query.core.proxy.sql;


import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.IncludeAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * create time 2025/6/26 20:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class Include<TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> implements IncludeAvailable {
    private String navValue;
    private TPropertyProxy proxyInstance;
    private final List<Function<ClientQueryable<?>, ClientQueryable<?>>> functions = new ArrayList<>();
    private List<IncludeAvailable> includes = new ArrayList<>();

    public Include(SQLQueryable<TPropertyProxy, TProperty> includeMany) {
        this.navValue = includeMany.getNavValue();
        this.proxyInstance = includeMany.getProxy();
        this.includes.add(this);
    }

    public Include(TPropertyProxy includeOne) {
        this.navValue = includeOne.getNavValue();
        this.proxyInstance = includeOne;
        this.includes.add(this);
    }

    public Include<TPropertyProxy, TProperty> where(SQLActionExpression1<TPropertyProxy> filter) {
        functions.add(q -> {
            return new EasyEntityQueryable<>(proxyInstance, EasyObjectUtil.typeCastNullable(q)).where(filter).getClientQueryable();
        });
        return this;
    }

    public Include<TPropertyProxy, TProperty> orderBy(SQLActionExpression1<TPropertyProxy> orderBy) {
        functions.add(q -> {
            return new EasyEntityQueryable<>(proxyInstance, EasyObjectUtil.typeCastNullable(q)).orderBy(orderBy).getClientQueryable();
        });
        return this;
    }

    public Include<TPropertyProxy, TProperty> limit(long offset, long rows) {
        functions.add(q -> {
            return new EasyEntityQueryable<>(proxyInstance, EasyObjectUtil.typeCastNullable(q)).limit(offset, rows).getClientQueryable();
        });
        return this;
    }

    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> Include<TPropertyProxy, TProperty> select(SQLFuncExpression1<TPropertyProxy, TRProxy> selector) {

        functions.add(q -> {
            return new EasyEntityQueryable<>(proxyInstance, EasyObjectUtil.typeCastNullable(q)).select(selector).getClientQueryable();
        });
        return this;
    }


    public <TProxy extends ProxyEntity<TProxy, TProp>, TProp extends ProxyEntityAvailable<TProp, TProxy>> ThenInclude<TProxy, TProp> thenInclude(SQLQueryable<TProxy, TProp> includeMany) {
        return new ThenInclude<>(includes, includeMany);
    }

    public <TProxy extends ProxyEntity<TProxy, TProp>, TProp extends ProxyEntityAvailable<TProp, TProxy>> ThenInclude<TProxy, TProp> thenInclude(TProxy includeOne) {
        return new ThenInclude<>(includes, includeOne);
    }

    @Override
    public String getNavValue() {
        return navValue;
    }

    @Override
    public List<Function<ClientQueryable<?>, ClientQueryable<?>>> getFunctions() {
        return functions;
    }

    @Override
    public List<IncludeAvailable> getIncludes() {
        return includes;
    }
}
