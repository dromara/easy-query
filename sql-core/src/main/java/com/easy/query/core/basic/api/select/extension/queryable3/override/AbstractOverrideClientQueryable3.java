package com.easy.query.core.basic.api.select.extension.queryable3.override;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
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
public abstract class AbstractOverrideClientQueryable3<T1, T2, T3> extends AbstractClientQueryable<T1> {
    protected final Class<T2> t2Class;
    protected final Class<T3> t3Class;

    public AbstractOverrideClientQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
    }

    public Class<T2> queryClass2() {
        return t2Class;
    }

    public Class<T3> queryClass3() {
        return t3Class;
    }
    protected abstract ClientQueryable3<T1, T2, T3> getClientQueryable3();

    @Override
    public ClientQueryable3<T1, T2, T3> cloneQueryable() {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().cloneQueryable(getClientQueryable3());
    }

    @Override
    public ClientQueryable3<T1, T2, T3> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getClientQueryable3();
    }

    @Override
    public <TProperty> ClientQueryable3<T1, T2, T3> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression1<ColumnGroupSelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> having(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getClientQueryable3();
    }


    @Override
    public <TProperty> ClientQueryable3<T1, T2, T3> include(boolean condition, SQLFuncExpression1<NavigateInclude<T1>, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> distinct(boolean condition) {
        super.distinct(condition);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> disableLogicDelete() {
        super.disableLogicDelete();
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> enableLogicDelete() {
        super.enableLogicDelete();
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> noInterceptor() {
        super.noInterceptor();
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useInterceptor(String name) {
        super.useInterceptor(name);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> noInterceptor(String name) {
        super.noInterceptor(name);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useInterceptor() {
        super.useInterceptor();
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> asTracking() {
        super.asTracking();
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> asNoTracking() {
        super.asNoTracking();
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getClientQueryable3();
    }

    @Override
    public ClientQueryable3<T1, T2, T3> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getClientQueryable3();
    }


    @Override
    public ClientQueryable3<T1, T2, T3> asAlias(String alias) {
        super.asAlias(alias);
        return getClientQueryable3();
    }
    @Override
    public ClientQueryable3<T1, T2, T3> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getClientQueryable3();
    }
    @Override
    public ClientQueryable3<T1, T2, T3> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getClientQueryable3();
    }
}
