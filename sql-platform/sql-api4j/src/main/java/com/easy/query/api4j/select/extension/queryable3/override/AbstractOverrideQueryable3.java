package com.easy.query.api4j.select.extension.queryable3.override;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.abstraction.AbstractQueryable;
import com.easy.query.api4j.select.extension.queryable3.Queryable3Available;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable3;
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
public abstract class AbstractOverrideQueryable3<T1, T2, T3> extends AbstractQueryable<T1> implements Queryable3Available<T1, T2, T3> {

    protected final ClientQueryable3<T1, T2, T3> entityQueryable3;

    public AbstractOverrideQueryable3(ClientQueryable3<T1, T2, T3> entityQueryable3) {
        super(entityQueryable3);
        this.entityQueryable3 = entityQueryable3;
    }

    @Override
    public Queryable3<T1, T2,T3> orderBy(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable3();
    }

    @Override
    public <TProperty> Queryable3<T1, T2,T3> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable3();
    }

    @Override
    public <TREntity> Queryable3<T1, T2,T3> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> noInterceptor() {
        super.noInterceptor();
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> useInterceptor() {
        super.useInterceptor();
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> asTracking() {
        super.asTracking();
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> asNoTracking() {
        super.asNoTracking();
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable3();
    }

    @Override
    public Queryable3<T1, T2,T3> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable3();
    }
    @Override
    public Queryable3<T1, T2,T3> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(conditionAccepter);
        return getQueryable3();
    }
}
