package com.easy.query.core.basic.api.select.extension.queryable2.override;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideClientQueryable2<T1,T2> extends AbstractClientQueryable<T1> {
    protected final Class<T2> t2Class;

    public AbstractOverrideClientQueryable2(Class<T1> t1Class, Class<T2> t2Class, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        super(t1Class, entityQueryExpressionBuilder);
        this.t2Class = t2Class;
    }
    public Class<T2> queryClass2() {
        return t2Class;
    }
    protected abstract ClientQueryable2<T1, T2> getClientQueryable2();


    @NotNull
    @Override
    public ClientQueryable2<T1, T2> cloneQueryable() {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().cloneQueryable(getClientQueryable2());
    }


    @Override
    public ClientQueryable2<T1, T2> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getClientQueryable2();
    }

    @Override
    public <TProperty> ClientQueryable2<T1, T2> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> where(boolean condition, SQLActionExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> orderByAsc(boolean condition, SQLActionExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable2();
    }
    @Override
    public ClientQueryable2<T1, T2> orderByDesc(boolean condition, SQLActionExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable2();
    }
    @Override
    public ClientQueryable2<T1, T2> groupBy(boolean condition, SQLActionExpression1<ColumnGroupSelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> having(boolean condition, SQLActionExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getClientQueryable2();
    }


    @Override
    public <TProperty> ClientQueryable2<T1, T2> include(boolean condition, SQLFuncExpression1<NavigateInclude, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getClientQueryable2();
    }

    @NotNull
    @Override
    public ClientQueryable2<T1, T2> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getClientQueryable2();
    }

    @NotNull
    @Override
    public ClientQueryable2<T1, T2> distinct(boolean condition) {
        super.distinct(condition);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> disableLogicDelete() {
        super.disableLogicDelete();
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> enableLogicDelete() {
        super.enableLogicDelete();
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> noInterceptor() {
        super.noInterceptor();
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> useInterceptor(String name) {
        super.useInterceptor(name);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> noInterceptor(String name) {
        super.noInterceptor(name);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> useInterceptor() {
        super.useInterceptor();
        return getClientQueryable2();
    }

    @NotNull
    @Override
    public ClientQueryable2<T1, T2> asTracking() {
        super.asTracking();
        return getClientQueryable2();
    }

    @NotNull
    @Override
    public ClientQueryable2<T1, T2> asNoTracking() {
        super.asNoTracking();
        return getClientQueryable2();
    }

    @NotNull
    @Override
    public ClientQueryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getClientQueryable2();
    }

    @NotNull
    @Override
    public ClientQueryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getClientQueryable2();
    }

    @NotNull
    @Override
    public ClientQueryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getClientQueryable2();
    }


    @Override
    public ClientQueryable2<T1, T2> asAlias(String alias) {
        super.asAlias(alias);
        return getClientQueryable2();
    }

    @Override
    public ClientQueryable2<T1, T2> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getClientQueryable2();
    }
    @Override
    public ClientQueryable2<T1, T2> asTableSegment(BiFunction<String, String, String> segmentAs) {
        super.asTableSegment(segmentAs);
        return getClientQueryable2();
    }
    @Override
    public ClientQueryable2<T1, T2> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getClientQueryable2();
    }
    @Override
    public ClientQueryable2<T1, T2> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        super.tableLogicDelete(tableLogicDel);
        return getClientQueryable2();
    }
    @Override
    public ClientQueryable2<T1, T2> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        super.configure(configurer);
        return getClientQueryable2();
    }
}
