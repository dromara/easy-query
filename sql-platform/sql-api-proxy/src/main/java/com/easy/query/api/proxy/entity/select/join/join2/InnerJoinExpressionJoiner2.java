package com.easy.query.api.proxy.entity.select.join.join2;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2025/5/22 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class InnerJoinExpressionJoiner2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ExpressionJoiner2<T1Proxy, T1> {
    public InnerJoinExpressionJoiner2(T1Proxy t1Proxy, ClientQueryable<T1> fromQueryable) {
        super(t1Proxy, fromQueryable);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> join(T2Proxy t2Proxy, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        ClientQueryable2<T1, T2> entityQueryable2 = joinBaseQueryable.innerJoin(t2Proxy.getEntityClass(), (t, t1) -> {
            invokeJoin(t.getFilter(), t1.getTable(), t2Proxy, onExpression);
        });
        return new EasyEntityQueryable2<>(t1Proxy, t2Proxy, entityQueryable2);
    }

    @Override
    public <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> join(EntityQueryable<T2Proxy, T2> joinQueryable, SQLActionExpression2<T1Proxy, T2Proxy> onExpression) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return join(joinQueryable.get1Proxy(),onExpression);
        }
        ClientQueryable<T2> clientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable2<T1, T2> entityQueryable2 = joinBaseQueryable.innerJoin(clientQueryable, (t, t1) -> {
            invokeJoin(t.getFilter(), t1.getTable(), joinQueryable.get1Proxy(), onExpression);
        });
        return new EasyEntityQueryable2<>(t1Proxy, joinQueryable.get1Proxy(), entityQueryable2);
    }
}
