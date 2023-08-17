package com.easy.query.api4j.select.extension.queryable4.override;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.select.abstraction.AbstractQueryable;
import com.easy.query.api4j.select.extension.queryable4.Queryable4Available;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable4;
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
public abstract class AbstractOverrideQueryable4<T1, T2, T3,T4> extends AbstractQueryable<T1> implements Queryable4Available<T1, T2, T3,T4> {

    protected final ClientQueryable4<T1, T2, T3,T4> entityQueryable4;

    public AbstractOverrideQueryable4(ClientQueryable4<T1, T2, T3,T4> entityQueryable4) {
        super(entityQueryable4);
        this.entityQueryable4 = entityQueryable4;
    }

    @Override
    public Queryable4<T1, T2,T3,T4> orderBy(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable4();
    }

    @Override
    public <TProperty> Queryable4<T1, T2,T3,T4> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable4();
    }

    @Override
    public <TREntity> Queryable4<T1, T2,T3,T4> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> noInterceptor() {
        super.noInterceptor();
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> useInterceptor() {
        super.useInterceptor();
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> asTracking() {
        super.asTracking();
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> asNoTracking() {
        super.asNoTracking();
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable4();
    }

    @Override
    public Queryable4<T1, T2,T3,T4> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable4();
    }
}
