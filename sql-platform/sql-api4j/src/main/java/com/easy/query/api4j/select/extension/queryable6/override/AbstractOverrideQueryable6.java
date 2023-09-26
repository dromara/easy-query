package com.easy.query.api4j.select.extension.queryable6.override;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable6;
import com.easy.query.api4j.select.abstraction.AbstractQueryable;
import com.easy.query.api4j.select.extension.queryable6.Queryable6Available;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable6;
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
public abstract class AbstractOverrideQueryable6<T1, T2, T3,T4,T5,T6> extends AbstractQueryable<T1> implements Queryable6Available<T1, T2, T3,T4,T5,T6> {

    protected final ClientQueryable6<T1, T2, T3,T4,T5,T6> entityQueryable6;

    public AbstractOverrideQueryable6(ClientQueryable6<T1, T2, T3,T4,T5,T6> entityQueryable6) {
        super(entityQueryable6);
        this.entityQueryable6 = entityQueryable6;
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> orderBy(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable6();
    }

    @Override
    public <TProperty> Queryable6<T1, T2,T3,T4,T5,T6> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable6();
    }

    @Override
    public <TREntity> Queryable6<T1, T2,T3,T4,T5,T6> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> noInterceptor() {
        super.noInterceptor();
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> useInterceptor() {
        super.useInterceptor();
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> asTracking() {
        super.asTracking();
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> asNoTracking() {
        super.asNoTracking();
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable6();
    }

    @Override
    public Queryable6<T1, T2,T3,T4,T5,T6> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable6();
    }
}
