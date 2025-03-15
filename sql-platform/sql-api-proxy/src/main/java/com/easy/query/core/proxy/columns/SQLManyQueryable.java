package com.easy.query.core.proxy.columns;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.internal.ExpressionConfigurable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2024/6/5 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLManyQueryable<TProxy, T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends SQLQueryable<T1Proxy, T1>, LogicDeletable<SQLManyQueryable<TProxy, T1Proxy, T1>> {

    //    void _setProxy(TProxy tProxy);

    default T1Proxy first(){
        return element(0);
    }
    T1Proxy element(int index);

    default SQLQueryable<T1Proxy, T1> elements(int fromIndex, int toIndex) {
        return elements(true, fromIndex, toIndex);
    }

    SQLQueryable<T1Proxy, T1> elements(boolean condition, int fromIndex, int toIndex);



    default SQLManyQueryable<TProxy, T1Proxy, T1> orderBy(SQLExpression1<T1Proxy> orderExpression) {
        return orderBy(true, orderExpression);
    }

    SQLManyQueryable<TProxy, T1Proxy, T1> orderBy(boolean condition, SQLExpression1<T1Proxy> orderExpression);

//    SQLQueryable<T1Proxy, T1> elements(int begin,int end);

    SQLManyQueryable<TProxy, T1Proxy, T1> where(SQLExpression1<T1Proxy> whereExpression);

    /**
     * 仅子查询配置生效
     * manyJoin下使用则会转成独立SubQuery
     *
     * @param configureExpression
     * @return
     */
    default SQLManyQueryable<TProxy, T1Proxy, T1> configure(SQLExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> configureExpression) {
        this.getSubQueryContext().appendConfigureExpression(configureExpression);
        return this;
    }


}
