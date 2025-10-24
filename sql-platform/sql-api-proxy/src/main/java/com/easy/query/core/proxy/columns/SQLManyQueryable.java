package com.easy.query.core.proxy.columns;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.internal.ExpressionConfigurable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.enums.SubQueryModeEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.columns.impl.DefaultSubquerySQLQueryableFactory;
import com.easy.query.core.proxy.impl.SQLColumnIncludeColumn2Impl;
import com.easy.query.core.proxy.sql.EasyIncludeQueryable;
import com.easy.query.core.proxy.sql.Include;
import com.easy.query.core.proxy.sql.IncludeQueryable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2024/6/5 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLManyQueryable<TProxy, T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends SQLQueryable<T1Proxy, T1>, LogicDeletable<SQLManyQueryable<TProxy, T1Proxy, T1>>,ProxyEntity<SQLManyQueryable<TProxy,T1Proxy,T1>,T1> {

    //    void _setProxy(TProxy tProxy);

    /**
     * 请使用{@link #first()}
     *
     * @return
     */
    @Deprecated
    default T1Proxy firstElement() {
        return element(0);
    }

    /**
     * 一对多或者多对多子查询获取第一个元素
     *
     * @return
     */
    default T1Proxy first() {
        return element(0);
    }

    T1Proxy element(int index);

    default SQLQueryable<T1Proxy, T1> elements(int fromIndex, int toIndex) {
        return elements(true, fromIndex, toIndex);
    }

    SQLQueryable<T1Proxy, T1> elements(boolean condition, int fromIndex, int toIndex);


    default SQLManyQueryable<TProxy, T1Proxy, T1> orderBy(SQLActionExpression1<T1Proxy> orderExpression) {
        return orderBy(true, orderExpression);
    }

    SQLManyQueryable<TProxy, T1Proxy, T1> orderBy(boolean condition, SQLActionExpression1<T1Proxy> orderExpression);

//    SQLQueryable<T1Proxy, T1> elements(int begin,int end);

    SQLManyQueryable<TProxy, T1Proxy, T1> where(SQLActionExpression1<T1Proxy> whereExpression);

    /**
     * 添加独立的条件和subQueryConfigure同理支持后续的子查询全部拥有该条件
     * 多次设置将只接受最后一次
     *
     * @param whereExpression
     */
    void filter(SQLActionExpression1<T1Proxy> whereExpression);

    void mode(SubQueryModeEnum subQueryMode);

    /**
     * 仅子查询配置生效
     * manyJoin下使用则会转成独立SubQuery
     *
     * @param configureExpression
     * @return
     */
    default SQLManyQueryable<TProxy, T1Proxy, T1> configure(SQLActionExpression1<ExpressionConfigurable<EntityQueryable<T1Proxy, T1>>> configureExpression) {
        this.getSubQueryContext().appendConfigureExpression(configureExpression);
        return this;
    }


    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TProxy set(SQLQueryable<TPropertyProxy, TProperty> columnProxy) {
        set(columnProxy, null);
        return EasyObjectUtil.typeCastNullable(this.getSubQueryContext().getLeftTableProxy());
    }

    /**
     * <blockquote><pre>
     * {@code
     *     new MyUserVOProxy()
     *        .vo1().set(user.name())
     *        .vo2().set(user.id())
     *        .vo3().set(user.phone())
     *        .cards().set(user.bankCards().where(bc -> bc.type().eq("储蓄卡")), (self, target) -> {
     *            self.type().set(target.code());
     *            self.code().set(target.bank().name());
     *        })
     * }
     * </pre></blockquote>
     *
     * @param columnProxy
     * @param navigateSelectExpression
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TProxy set(SQLQueryable<TPropertyProxy, TProperty> columnProxy, SQLActionExpression2<T1Proxy, TPropertyProxy> navigateSelectExpression) {
        DefaultSubquerySQLQueryableFactory.dslNavigatesSet(columnProxy);

        T1Proxy propertyProxy = this.getSubQueryContext().getPropertyProxy();
        T1Proxy t1Proxy = propertyProxy.create(null, this.getEntitySQLContext());
        getEntitySQLContext().accept(new SQLColumnIncludeColumn2Impl<>(columnProxy.getOriginalTable(), columnProxy.getNavValue(), getNavValue(), columnProxy.getProxy(), t1Proxy, navigateSelectExpression));
        return EasyObjectUtil.typeCastNullable(this.getSubQueryContext().getLeftTableProxy());
    }

    default IncludeQueryable<T1Proxy, T1> asIncludeQueryable() {
        return new EasyIncludeQueryable<>(this);
    }

}