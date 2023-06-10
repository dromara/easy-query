package com.easy.query.api4kt.select.abstraction;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable4;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtColumnSelector;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtColumnAsSelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtColumnSelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:38
 */
public abstract class AbstractKtQueryable4<T1, T2, T3, T4> extends AbstractKtQueryable<T1> implements KtQueryable4<T1, T2, T3, T4> {
    protected final ClientQueryable4<T1, T2, T3, T4> entityQueryable4;


    public AbstractKtQueryable4(ClientQueryable4<T1, T2, T3, T4> entityQueryable4) {
        super(entityQueryable4);
        this.entityQueryable4 = entityQueryable4;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> getClientQueryable4() {
        return entityQueryable4;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> whereExpression) {
        if (condition) {
            entityQueryable4.where((where1, where2, where3, where4) -> {
                whereExpression.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
            });
        }
        return this;
    }

    @Override
    public <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression4<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>, SQLKtColumnAsSelector<T3, TR>, SQLKtColumnAsSelector<T4, TR>> selectExpression) {
        ClientQueryable<TR> select = entityQueryable4.select(resultClass, (selector1, selector2, selector3, selector4) -> {
            selectExpression.apply(new SQLKtColumnAsSelectorImpl<>(selector1), new SQLKtColumnAsSelectorImpl<>(selector2), new SQLKtColumnAsSelectorImpl<>(selector3), new SQLKtColumnAsSelectorImpl<>(selector4));
        });
        return new EasyKtQueryable<>(select);
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression4<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>, SQLKtGroupBySelector<T4>> selectExpression) {
        if (condition) {
            entityQueryable4.groupBy((selector1, selector2, selector3, selector4) -> {
                selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3), new SQLKtGroupBySelectorImpl<>(selector4));
            });
        }
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression1<SQLKtColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression4<SQLKtColumnSelector<T1>, SQLKtColumnSelector<T2>, SQLKtColumnSelector<T3>, SQLKtColumnSelector<T4>> selectExpression) {
        if (condition) {
            entityQueryable4.orderByAsc((selector1, selector2, selector3, selector4) -> {
                selectExpression.apply(new SQLKtColumnSelectorImpl<>(selector1), new SQLKtColumnSelectorImpl<>(selector2), new SQLKtColumnSelectorImpl<>(selector3), new SQLKtColumnSelectorImpl<>(selector4));
            });
        }
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression1<SQLKtColumnSelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression4<SQLKtColumnSelector<T1>, SQLKtColumnSelector<T2>, SQLKtColumnSelector<T3>, SQLKtColumnSelector<T4>> selectExpression) {
        if (condition) {
            entityQueryable4.orderByDesc((selector1, selector2, selector3, selector4) -> {
                selectExpression.apply(new SQLKtColumnSelectorImpl<>(selector1), new SQLKtColumnSelectorImpl<>(selector2), new SQLKtColumnSelectorImpl<>(selector3), new SQLKtColumnSelectorImpl<>(selector4));
            });
        }
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }

    @Override
    public KtQueryable4<T1, T2, T3, T4> asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }
}
