package com.easy.query.core.basic.api.select.extension.queryable5.override;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideClientQueryable5<T1, T2, T3, T4, T5> extends AbstractClientQueryable<T1> {
    protected final Class<T2> t2Class;
    protected final Class<T3> t3Class;
    protected final Class<T4> t4Class;
    protected final Class<T5> t5Class;

    public AbstractOverrideClientQueryable5(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
        this.t4Class = t4Class;
        this.t5Class = t5Class;
    }

    protected abstract ClientQueryable5<T1, T2, T3, T4, T5> getClientQueryable5();


    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getClientQueryable5();
    }

    @Override
    public <TProperty> ClientQueryable5<T1, T2, T3, T4, T5> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> orderByAsc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> orderByDesc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> groupBy(boolean condition, SQLExpression1<ColumnGroupSelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> having(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getClientQueryable5();
    }


    @Override
    public <TProperty> ClientQueryable5<T1, T2, T3, T4, T5> include(boolean condition, SQLFuncExpression1<NavigateInclude<T1>, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> distinct(boolean condition) {
        super.distinct(condition);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> disableLogicDelete() {
        super.disableLogicDelete();
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> enableLogicDelete() {
        super.enableLogicDelete();
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> noInterceptor() {
        super.noInterceptor();
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> useInterceptor(String name) {
        super.useInterceptor(name);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> noInterceptor(String name) {
        super.noInterceptor(name);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> useInterceptor() {
        super.useInterceptor();
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> asTracking() {
        super.asTracking();
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> asNoTracking() {
        super.asNoTracking();
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getClientQueryable5();
    }

    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getClientQueryable5();
    }


    @Override
    public ClientQueryable5<T1, T2, T3, T4, T5> asAlias(String alias) {
        super.asAlias(alias);
        return getClientQueryable5();
    }
}
