package com.easy.query.api4kt.select.extension.queryable3.override;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable3;
import com.easy.query.api4kt.select.abstraction.AbstractKtQueryable;
import com.easy.query.api4kt.select.extension.queryable3.KtQueryable3Available;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtNavigateInclude;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ConditionAccepter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

import java.util.Collection;
import java.util.function.Function;

;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideKtQueryable3<T1, T2, T3> extends AbstractKtQueryable<T1> implements KtQueryable3Available<T1, T2, T3> {

    protected final ClientQueryable3<T1, T2, T3> entityQueryable3;

    public AbstractOverrideKtQueryable3(ClientQueryable3<T1, T2, T3> entityQueryable3) {
        super(entityQueryable3);
        this.entityQueryable3 = entityQueryable3;
    }

    @Override
    public KtQueryable3<T1, T2,T3> orderBy(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression, boolean asc) {
        super.orderBy(condition, selectExpression, asc);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition, configuration);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getQueryable3();
    }

    @Override
    public <TProperty> KtQueryable3<T1, T2,T3> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return getQueryable3();
    }

    @Override
    public <TREntity> KtQueryable3<T1, T2,T3> include(boolean condition, SQLFuncExpression1<SQLKtNavigateInclude<T1>, KtQueryable<TREntity>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> distinct(boolean condition) {
        super.distinct(condition);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> disableLogicDelete() {
        super.disableLogicDelete();
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> enableLogicDelete() {
        super.enableLogicDelete();
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> noInterceptor() {
        super.noInterceptor();
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> useInterceptor(String name) {
        super.useInterceptor(name);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> noInterceptor(String name) {
        super.noInterceptor(name);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> useInterceptor() {
        super.useInterceptor();
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> asTracking() {
        super.asTracking();
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> asNoTracking() {
        super.asNoTracking();
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> asAlias(String alias) {
        super.asAlias(alias);
        return getQueryable3();
    }

    @Override
    public KtQueryable3<T1, T2,T3> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getQueryable3();
    }
    @Override
    public KtQueryable3<T1,T2,T3> conditionConfigure(ConditionAccepter conditionAccepter) {
        super.conditionConfigure(conditionAccepter);
        return getQueryable3();
    }
}
