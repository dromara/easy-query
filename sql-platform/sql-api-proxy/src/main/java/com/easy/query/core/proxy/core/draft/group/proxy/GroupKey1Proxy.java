package com.easy.query.core.proxy.core.draft.group.proxy;

import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.group.GroupKey1;
import com.easy.query.core.proxy.extension.ColumnAggregatable;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.fetcher.AbstractFetcher;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/23 13:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupKey1Proxy<T1> extends AbstractProxyEntity<GroupKey1Proxy<T1>, GroupKey1<T1>> {

    private static final Class<GroupKey1> entityClass = GroupKey1.class;

    public static <T> GroupKey1Proxy<T> createTable() {
        return new GroupKey1Proxy<>();
    }

    public GroupKey1Proxy() {
    }

    /**
     * {@link Draft1#getValue1}
     */
    public SQLColumn<GroupKey1Proxy<T1>, T1> key1() {
        return get("value1");
    }
    public <TProperty,TResult extends Long> ColumnFuncComparableExpression<TResult> count(SQLFuncExpression1<TSourceProxy, ColumnAggregatable<TProperty>> column){
        return column.apply(tSourceProxy).count();
    }


    @Override
    public Class<GroupKey1<T1>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }


    /**
     * 数据库列的简单获取
     *
     * @return
     */
    public GroupKeyDraft1ProxyFetcher<T1> FETCHER = new GroupKeyDraft1ProxyFetcher<>(this, null, SQLSelectAsExpression.empty);


    public static class GroupKeyDraft1ProxyFetcher<TF1> extends AbstractFetcher<GroupKey1Proxy<TF1>, GroupKey1<TF1>, GroupKeyDraft1ProxyFetcher<TF1>> {

        public GroupKeyDraft1ProxyFetcher(GroupKey1Proxy<TF1> proxy, GroupKeyDraft1ProxyFetcher<TF1> prev, SQLSelectAsExpression sqlSelectAsExpression) {
            super(proxy, prev, sqlSelectAsExpression);
        }


        /**
         * {@link Draft1#getValue1}
         */
        public GroupKeyDraft1ProxyFetcher<TF1> key1() {
            return add(getProxy().key1());
        }


        @Override
        protected GroupKeyDraft1ProxyFetcher<TF1> createFetcher(
                GroupKey1Proxy<TF1> cp,
                AbstractFetcher<GroupKey1Proxy<TF1>, GroupKey1<TF1>, GroupKeyDraft1ProxyFetcher<TF1>> prev,
                SQLSelectAsExpression sqlSelectExpression
        ) {
            return new GroupKeyDraft1ProxyFetcher<>(cp, this, sqlSelectExpression);
        }
    }

}
