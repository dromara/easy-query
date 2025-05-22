package com.easy.query.api.proxy.entity.select.join.join2;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasySQLJoinUtil;

/**
 * create time 2025/5/22 08:43
 * 两张表进行join
 *
 * @author xuejiaming
 */
public abstract class ExpressionJoiner2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    protected final T1Proxy t1Proxy;
    protected final ClientQueryable<T1> joinBaseQueryable;

    public ExpressionJoiner2(T1Proxy t1Proxy, ClientQueryable<T1> fromQueryable) {
        int tableSize = fromQueryable.getSQLEntityExpressionBuilder().getTables().size();
        if (tableSize >= 2) {
            this.joinBaseQueryable = fromQueryable.select(fromQueryable.queryClass());
        }else{
            this.joinBaseQueryable = EasySQLJoinUtil.getJoinBaseQueryable(fromQueryable);
        }
        EntityTableExpressionBuilder table = joinBaseQueryable.getSQLEntityExpressionBuilder().getFromTable();

        this.t1Proxy = t1Proxy.create(table.getEntityTable(), joinBaseQueryable.getSQLEntityExpressionBuilder());
    }

    public abstract <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> join(T2Proxy t2Proxy, SQLActionExpression2<T1Proxy, T2Proxy> onExpression);

    public abstract <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> join(EntityQueryable<T2Proxy, T2> joinQueryable, SQLActionExpression2<T1Proxy, T2Proxy> onExpression);

    protected <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> void invokeJoin(Filter filter, TableAvailable t2Table, T2Proxy t2Proxy, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        t1Proxy.getEntitySQLContext()._where(filter, () -> {
            onExpression.apply(t1Proxy, t2Proxy.create(t2Table, t1Proxy.getEntitySQLContext()));
        });
    }
}
