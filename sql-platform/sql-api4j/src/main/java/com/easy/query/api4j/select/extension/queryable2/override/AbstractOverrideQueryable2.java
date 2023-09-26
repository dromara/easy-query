package com.easy.query.api4j.select.extension.queryable2.override;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.select.abstraction.AbstractQueryable;
import com.easy.query.api4j.select.extension.queryable2.Queryable2Available;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLNavigateInclude;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable2;
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
public abstract class AbstractOverrideQueryable2<T1,T2> extends AbstractQueryable<T1> implements Queryable2Available<T1,T2> {

    protected final ClientQueryable2<T1, T2> entityQueryable2;
    public AbstractOverrideQueryable2(ClientQueryable2<T1, T2> entityQueryable2) {
        super(entityQueryable2);
        this.entityQueryable2=entityQueryable2;
    }

    @Override
    public Queryable2<T1,T2> orderBy(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition,selectExpression,asc);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1,T2> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition,configuration);
        return getQueryable2();
    }
    @Override
    public Queryable2<T1, T2> whereById(boolean condition, Object id) {
        super.whereById(condition,id);
        return getQueryable2();
    }

    @Override
    public <TProperty> Queryable2<T1, T2> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition,ids);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> whereObject(boolean condition, Object object) {
        super.whereObject(condition,object);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition,whereExpression);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition,selectExpression);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition,predicateExpression);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition,selectExpression);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition,selectExpression);
        return getQueryable2();
    }

    @Override
    public <TREntity> Queryable2<T1, T2> include(boolean condition, SQLFuncExpression1<SQLNavigateInclude<T1>, Queryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition,navigateIncludeSQLExpression);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> limit(boolean condition, long offset, long rows) {
        super.limit(condition,offset,rows);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> noInterceptor() {
        super.noInterceptor();
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> useInterceptor() {
        super.useInterceptor();
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> asTracking() {
        super.asTracking();
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> asNoTracking() {
        super.asNoTracking();
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit,connectionMode);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable2();
    }

    @Override
    public Queryable2<T1, T2> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable2();
    }
    @Override
    public Queryable2<T1, T2> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getQueryable2();
    }
}
