package com.easy.query.api4kt.select.extension.queryable7.override;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable7;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable;
import com.easy.query.api4kt.select.extension.queryable7.KtQueryable7Available;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtNavigateInclude;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable7;
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
public abstract class AbstractOverrideKtQueryable7<T1, T2, T3,T4,T5,T6,T7> extends AbstractKtQueryable<T1> implements KtQueryable7Available<T1, T2, T3,T4,T5,T6,T7> {

    protected final ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> entityQueryable7;

    public AbstractOverrideKtQueryable7(ClientQueryable7<T1, T2, T3,T4,T5,T6,T7> entityQueryable7) {
        super(entityQueryable7);
        this.entityQueryable7 = entityQueryable7;
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> orderBy(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable7();
    }

    @Override
    public <TProperty> KtQueryable7<T1, T2,T3,T4,T5,T6,T7> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable7();
    }

    @Override
    public <TREntity> KtQueryable7<T1, T2,T3,T4,T5,T6,T7> include(boolean condition, SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> noInterceptor() {
        super.noInterceptor();
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> useInterceptor() {
        super.useInterceptor();
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> asTracking() {
        super.asTracking();
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> asNoTracking() {
        super.asNoTracking();
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable7();
    }

    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable7();
    }
    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable7();
    }
    @Override
    public KtQueryable7<T1, T2,T3,T4,T5,T6,T7> behaviorConfigure(SQLExpression1<EasyBehavior> configure) {
        super.behaviorConfigure(configure);
        return getQueryable7();
    }
}
