package com.easy.query.api4j.select.extension.queryable8.override;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable8;
import com.easy.query.api4j.select.abstraction.AbstractQueryable;
import com.easy.query.api4j.select.extension.queryable8.Queryable8Available;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> extends AbstractQueryable<T1> implements Queryable8Available<T1, T2, T3, T4, T5, T6, T7, T8> {

    protected final ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8;

    public AbstractOverrideQueryable8(ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8) {
        super(entityQueryable8);
        this.entityQueryable8 = entityQueryable8;
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderBy(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable8();
    }

    @Override
    public <TProperty> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable8();
    }

    @Override
    public <TREntity> Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> noInterceptor() {
        super.noInterceptor();
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> useInterceptor() {
        super.useInterceptor();
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTracking() {
        super.asTracking();
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> asNoTracking() {
        super.asNoTracking();
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable8();
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> configure(SQLExpression1<ContextConfigurer> configurer) {
        super.configure(configurer);
        return getQueryable8();
    }
}
