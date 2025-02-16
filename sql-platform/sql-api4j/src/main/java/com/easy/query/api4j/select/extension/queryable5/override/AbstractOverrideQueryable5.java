package com.easy.query.api4j.select.extension.queryable5.override;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable5;
import com.easy.query.api4j.select.abstraction.AbstractQueryable;
import com.easy.query.api4j.select.extension.queryable5.Queryable5Available;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideQueryable5<T1, T2, T3, T4, T5> extends AbstractQueryable<T1> implements Queryable5Available<T1, T2, T3, T4, T5> {

    protected final ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5;

    public AbstractOverrideQueryable5(ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5) {
        super(entityQueryable5);
        this.entityQueryable5 = entityQueryable5;
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> orderBy(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable5();
    }

    @Override
    public <TProperty> Queryable5<T1, T2, T3, T4, T5> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable5();
    }

    @Override
    public <TREntity> Queryable5<T1, T2, T3, T4, T5> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> noInterceptor() {
        super.noInterceptor();
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> useInterceptor() {
        super.useInterceptor();
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> asTracking() {
        super.asTracking();
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> asNoTracking() {
        super.asNoTracking();
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> asTableSegment(BiFunction<String, String, String> segmentAs) {
        super.asTableSegment(segmentAs);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable5();
    }

    @Override
    public Queryable5<T1, T2, T3, T4, T5> configure(SQLExpression1<ContextConfigurer> configurer) {
        super.configure(configurer);
        return getQueryable5();
    }
}
