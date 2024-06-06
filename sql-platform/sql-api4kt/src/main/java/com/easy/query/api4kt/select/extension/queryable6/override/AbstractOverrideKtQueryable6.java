package com.easy.query.api4kt.select.extension.queryable6.override;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable6;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable;
import com.easy.query.api4kt.select.extension.queryable6.KtQueryable6Available;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtNavigateInclude;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideKtQueryable6<T1, T2, T3, T4, T5, T6> extends AbstractKtQueryable<T1> implements KtQueryable6Available<T1, T2, T3, T4, T5, T6> {

    protected final ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6;

    public AbstractOverrideKtQueryable6(ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6) {
        super(entityQueryable6);
        this.entityQueryable6 = entityQueryable6;
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> orderBy(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable6();
    }

    @Override
    public <TProperty> KtQueryable6<T1, T2, T3, T4, T5, T6> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable6();
    }

    @Override
    public <TREntity> KtQueryable6<T1, T2, T3, T4, T5, T6> include(boolean condition, SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> noInterceptor() {
        super.noInterceptor();
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> useInterceptor() {
        super.useInterceptor();
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> asTracking() {
        super.asTracking();
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> asNoTracking() {
        super.asNoTracking();
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable6();
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> behaviorConfigure(SQLExpression1<EasyBehavior> configure) {
        super.behaviorConfigure(configure);
        return getQueryable6();
    }
}
