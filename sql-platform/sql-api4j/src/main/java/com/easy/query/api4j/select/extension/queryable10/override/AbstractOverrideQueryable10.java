package com.easy.query.api4j.select.extension.queryable10.override;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable10;
import com.easy.query.api4j.select.abstraction.AbstractQueryable;
import com.easy.query.api4j.select.extension.queryable10.Queryable10Available;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable10;
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
public abstract class AbstractOverrideQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends AbstractQueryable<T1> implements Queryable10Available<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    protected final ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10;

    public AbstractOverrideQueryable10(ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10) {
        super(entityQueryable10);
        this.entityQueryable10 = entityQueryable10;
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderBy(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable10();
    }

    @Override
    public <TProperty> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable10();
    }

    @Override
    public <TREntity> Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> noInterceptor() {
        super.noInterceptor();
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> useInterceptor() {
        super.useInterceptor();
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> asTracking() {
        super.asTracking();
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> asNoTracking() {
        super.asNoTracking();
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable10();
    }

    @Override
    public Queryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable10();
    }
}
