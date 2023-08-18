package com.easy.query.api4kt.select.extension.queryable5.override;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable5;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable;
import com.easy.query.api4kt.select.extension.queryable5.KtQueryable5Available;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtNavigateInclude;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideKtQueryable5<T1, T2, T3,T4,T5> extends AbstractKtQueryable<T1> implements KtQueryable5Available<T1, T2, T3,T4,T5> {

    protected final ClientQueryable5<T1, T2, T3,T4,T5> entityQueryable5;

    public AbstractOverrideKtQueryable5(ClientQueryable5<T1, T2, T3,T4,T5> entityQueryable5) {
        super(entityQueryable5);
        this.entityQueryable5 = entityQueryable5;
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> orderBy(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable5();
    }

    @Override
    public <TProperty> KtQueryable5<T1, T2,T3,T4,T5> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable5();
    }

    @Override
    public <TREntity> KtQueryable5<T1, T2,T3,T4,T5> include(boolean condition, SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> noInterceptor() {
        super.noInterceptor();
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> useInterceptor() {
        super.useInterceptor();
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> asTracking() {
        super.asTracking();
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> asNoTracking() {
        super.asNoTracking();
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable5();
    }

    @Override
    public KtQueryable5<T1, T2,T3,T4,T5> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable5();
    }
}
