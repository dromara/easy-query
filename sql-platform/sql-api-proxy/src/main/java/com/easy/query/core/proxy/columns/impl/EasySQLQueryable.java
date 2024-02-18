package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2024/2/11 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySQLQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLQueryable<T1Proxy, T1> {
    private final EntitySQLContext entitySQLContext;
    private final EasyEntityQueryable<T1Proxy, T1> easyEntityQueryable;
    private final TableAvailable originalTable;
    private List<SQLExpression1<EntityQueryable<T1Proxy, T1>>> replyExpressions;

    public EasySQLQueryable(EntitySQLContext entitySQLContext, EasyEntityQueryable<T1Proxy, T1> easyEntityQueryable, TableAvailable originalTable) {

        this.entitySQLContext = entitySQLContext;
        this.easyEntityQueryable = easyEntityQueryable;
        this.originalTable = originalTable;
        this.replyExpressions = new ArrayList<>();
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

    @Override
    public EasyEntityQueryable<T1Proxy, T1> getQueryable() {
        return easyEntityQueryable;
    }

    @Override
    public TableAvailable getOriginalTable() {
        return originalTable;
    }

    @Override
    public String getNavValue() {
        return easyEntityQueryable.get1Proxy().getNavValue();
    }

    @Override
    public void reply(EntityQueryable<T1Proxy, T1> queryable) {
        for (SQLExpression1<EntityQueryable<T1Proxy, T1>> replyExpression : replyExpressions) {
            replyExpression.apply(queryable);
        }
    }

    @Override
    public SQLQueryable<T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression) {
        replyExpressions.add(queryable->{
            queryable.where(whereExpression);
        });
        return this;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> limit(long rows) {
        replyExpressions.add(queryable->{
            queryable.limit(rows);
        });
        return this;
    }

    @Override
    public SQLQueryable<T1Proxy, T1> limit(long offset, long rows) {
        replyExpressions.add(queryable->{
            queryable.limit(offset,rows);
        });
        return this;
    }

    @Override
    public <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> void set(SQLQueryable<TPropertyProxy, TProperty> columnProxy, SQLFuncExpression1<ProxyEntity<TPropertyProxy, TProperty>, ProxyEntity<T1Proxy, T1>> navigateSelectExpression) {
        throw new UnsupportedOperationException();
    }
}
