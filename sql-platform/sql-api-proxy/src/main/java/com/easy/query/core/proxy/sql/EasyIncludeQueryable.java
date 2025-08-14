package com.easy.query.core.proxy.sql;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * create time 2025/8/14 19:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyIncludeQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements IncludeQueryable<T1Proxy, T1> {
    private final T1Proxy t1Proxy;
    private final String navValue;
    private final List<Function<ClientQueryable<?>, ClientQueryable<?>>> functions = new ArrayList<>();

    public EasyIncludeQueryable(T1Proxy t1Proxy) {
        this.navValue = t1Proxy.getNavValue();
        this.t1Proxy = t1Proxy;
    }

    public EasyIncludeQueryable(SQLQueryable<T1Proxy, T1> includeMany) {
        this.navValue = includeMany.getNavValue();
        this.t1Proxy = includeMany.getProxy();
        SubQueryContext<T1Proxy, T1> subQueryContext = includeMany.getSubQueryContext();
        SQLActionExpression1<T1Proxy> whereExpression = subQueryContext.getWhereExpression();
        if (whereExpression != null) {
            this.where(whereExpression);
        }
        SQLActionExpression1<T1Proxy> orderByExpression = subQueryContext.getOrderByExpression();
        if (orderByExpression != null) {
            this.orderBy(orderByExpression);
        }
        if (subQueryContext.hasElements()) {
            long limit = subQueryContext.getLimit();
            long offset = subQueryContext.getOffset();
            this.limit(offset, limit);
        }
    }

    @Override
    public String getNavValue() {
        return navValue;
    }

    @Override
    public T1Proxy getProxy() {
        return t1Proxy;
    }

    @Override
    public IncludeQueryable<T1Proxy, T1> where(SQLActionExpression1<T1Proxy> filter) {
        functions.add(q -> {
            return new EasyEntityQueryable<>(t1Proxy, EasyObjectUtil.typeCastNullable(q)).where(filter).getClientQueryable();
        });
        return this;
    }

    @Override
    public IncludeQueryable<T1Proxy, T1> orderBy(SQLActionExpression1<T1Proxy> orderBy) {
        functions.add(q -> {
            return new EasyEntityQueryable<>(t1Proxy, EasyObjectUtil.typeCastNullable(q)).orderBy(orderBy).getClientQueryable();
        });
        return this;
    }

    @Override
    public IncludeQueryable<T1Proxy, T1> limit(long offset, long rows) {
        functions.add(q -> {
            return new EasyEntityQueryable<>(t1Proxy, EasyObjectUtil.typeCastNullable(q)).limit(offset, rows).getClientQueryable();
        });
        return this;
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> IncludeQueryable<T1Proxy, T1> select(SQLFuncExpression1<T1Proxy, TRProxy> selector) {
        functions.add(q -> {
            return new EasyEntityQueryable<>(t1Proxy, EasyObjectUtil.typeCastNullable(q)).select(selector).getClientQueryable();
        });
        return this;
    }

    @Override
    public List<Function<ClientQueryable<?>, ClientQueryable<?>>> getFunctions() {
        return functions;
    }
}
