package com.easy.query.core.proxy.sql.include;


import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.IncludeAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
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
public class DefaultNavigateInclude<TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> implements NavigateInclude<TPropertyProxy, TProperty>, IncludeAvailable {
    private String navValue;
    private TPropertyProxy proxyInstance;
    private final List<Function<ClientQueryable<?>, ClientQueryable<?>>> functions = new ArrayList<>();

    public DefaultNavigateInclude(SQLQueryable<TPropertyProxy, TProperty> includeMany) {
        this.navValue = includeMany.getNavValue();
        this.proxyInstance = includeMany.getProxy();
        SubQueryContext<TPropertyProxy, TProperty> subQueryContext = includeMany.getSubQueryContext();
        SQLActionExpression1<TPropertyProxy> whereExpression = subQueryContext.getWhereExpression();
        if (whereExpression != null) {
            this.where(whereExpression);
        }
        SQLActionExpression1<TPropertyProxy> orderByExpression = subQueryContext.getOrderByExpression();
        if (orderByExpression != null) {
            this.orderBy(orderByExpression);
        }
        if (subQueryContext.hasElements()) {
            long limit = subQueryContext.getLimit();
            long offset = subQueryContext.getOffset();
            this.limit(offset, limit);
        }
    }

    public DefaultNavigateInclude(TPropertyProxy includeOne) {
        this.navValue = includeOne.getNavValue();
        this.proxyInstance = includeOne;
    }

    public DefaultNavigateInclude<TPropertyProxy, TProperty> where(SQLActionExpression1<TPropertyProxy> filter) {
        functions.add(q -> {
            return new EasyEntityQueryable<>(proxyInstance, EasyObjectUtil.typeCastNullable(q)).where(filter).getClientQueryable();
        });
        return this;
    }

    public DefaultNavigateInclude<TPropertyProxy, TProperty> orderBy(SQLActionExpression1<TPropertyProxy> orderBy) {
        functions.add(q -> {
            return new EasyEntityQueryable<>(proxyInstance, EasyObjectUtil.typeCastNullable(q)).orderBy(orderBy).getClientQueryable();
        });
        return this;
    }

    public DefaultNavigateInclude<TPropertyProxy, TProperty> limit(long offset, long rows) {
        functions.add(q -> {
            return new EasyEntityQueryable<>(proxyInstance, EasyObjectUtil.typeCastNullable(q)).limit(offset, rows).getClientQueryable();
        });
        return this;
    }

    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR> DefaultNavigateInclude<TPropertyProxy, TProperty> select(SQLFuncExpression1<TPropertyProxy, TRProxy> selector) {

        functions.add(q -> {
            return new EasyEntityQueryable<>(proxyInstance, EasyObjectUtil.typeCastNullable(q)).select(selector).getClientQueryable();
        });
        return this;
    }


    @Override
    public String getNavValue() {
        return navValue;
    }

    @Override
    public List<Function<ClientQueryable<?>, ClientQueryable<?>>> getFunctions() {
        return functions;
    }
}
