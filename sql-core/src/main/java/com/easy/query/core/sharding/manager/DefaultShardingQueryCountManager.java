package com.easy.query.core.sharding.manager;

import com.easy.query.core.basic.jdbc.tx.DefaultTransaction;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQuerySQLException;

import java.util.List;

/**
 * create time 2023/5/12 21:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultShardingQueryCountManager implements ShardingQueryCountManager {
    private final ThreadLocal<QueryCountContext> context = ThreadLocal.withInitial(() -> null);

    @Override
    public void begin() {
        if (context.get() != null) {
            throw new EasyQuerySQLException("repeat begin sharding query count");
        }
        QueryCountContext queryCountContext = new QueryCountContext();
        context.set(queryCountContext);
    }

    @Override
    public boolean isBegin() {
        return context.get()!=null;
    }

    private QueryCountContext getQueryCountContextNotNull() {
        QueryCountContext queryCountContext = context.get();
        if (queryCountContext == null) {
            throw new EasyQueryException("cant get query count context");
        }
        return queryCountContext;
    }

    @Override
    public void addCountResult(QueryCountResult queryCountResult) {
        QueryCountContext queryCountContext = getQueryCountContextNotNull();
        queryCountContext.addCountResult(queryCountResult);
    }

    @Override
    public List<QueryCountResult> getCountResult() {
        QueryCountContext queryCountContext = getQueryCountContextNotNull();
        return queryCountContext.getCountResult();
    }

    @Override
    public void clear() {
        context.remove();
    }
}
