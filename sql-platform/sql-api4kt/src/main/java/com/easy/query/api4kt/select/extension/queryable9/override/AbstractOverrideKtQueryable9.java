package com.easy.query.api4kt.select.extension.queryable9.override;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable9;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable;
import com.easy.query.api4kt.select.extension.queryable9.KtQueryable9Available;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtNavigateInclude;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
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
public abstract class AbstractOverrideKtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends AbstractKtQueryable<T1> implements KtQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    protected final ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9;

    public AbstractOverrideKtQueryable9(ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9) {
        super(entityQueryable9);
        this.entityQueryable9 = entityQueryable9;
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderBy(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable9();
    }

    @Override
    public <TProperty> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable9();
    }

    @Override
    public <TREntity> KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> include(boolean condition, SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> noInterceptor() {
        super.noInterceptor();
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useInterceptor() {
        super.useInterceptor();
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asTracking() {
        super.asTracking();
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asNoTracking() {
        super.asNoTracking();
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable9();
    }

    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable9();
    }
    @Override
    public KtQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(conditionAccepter);
        return getQueryable9();
    }
}
