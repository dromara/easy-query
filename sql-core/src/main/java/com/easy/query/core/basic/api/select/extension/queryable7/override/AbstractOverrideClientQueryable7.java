package com.easy.query.core.basic.api.select.extension.queryable7.override;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable7;
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
public abstract class AbstractOverrideClientQueryable7<T1, T2, T3, T4, T5, T6, T7> extends AbstractClientQueryable<T1> {
    protected final Class<T2> t2Class;
    protected final Class<T3> t3Class;
    protected final Class<T4> t4Class;
    protected final Class<T5> t5Class;
    protected final Class<T6> t6Class;
    protected final Class<T7> t7Class;

    public AbstractOverrideClientQueryable7(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
        this.t4Class = t4Class;
        this.t5Class = t5Class;
        this.t6Class = t6Class;
        this.t7Class = t7Class;
    }


    public Class<T2> queryClass2() {
        return t2Class;
    }

    public Class<T3> queryClass3() {
        return t3Class;
    }

    public Class<T4> queryClass4() {
        return t4Class;
    }

    public Class<T5> queryClass5() {
        return t5Class;
    }

    public Class<T6> queryClass6() {
        return t6Class;
    }

    public Class<T7> queryClass7() {
        return t7Class;
    }

    protected abstract ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> getClientQueryable7();


    @NotNull
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> cloneQueryable() {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().cloneQueryable(getClientQueryable7());
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getClientQueryable7();
    }

    @Override
    public <TProperty> ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> where(boolean condition, SQLActionExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAsc(boolean condition, SQLActionExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDesc(boolean condition, SQLActionExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> groupBy(boolean condition, SQLActionExpression1<ColumnGroupSelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> having(boolean condition, SQLActionExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getClientQueryable7();
    }


    @Override
    public <TProperty> ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> include(boolean condition, SQLFuncExpression1<NavigateInclude, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getClientQueryable7();
    }

    @NotNull
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getClientQueryable7();
    }

    @NotNull
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> distinct(boolean condition) {
        super.distinct(condition);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> disableLogicDelete() {
        super.disableLogicDelete();
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> enableLogicDelete() {
        super.enableLogicDelete();
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> noInterceptor() {
        super.noInterceptor();
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> useInterceptor(String name) {
        super.useInterceptor(name);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> noInterceptor(String name) {
        super.noInterceptor(name);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> useInterceptor() {
        super.useInterceptor();
        return getClientQueryable7();
    }

    @NotNull
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> asTracking() {
        super.asTracking();
        return getClientQueryable7();
    }

    @NotNull
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> asNoTracking() {
        super.asNoTracking();
        return getClientQueryable7();
    }

    @NotNull
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getClientQueryable7();
    }

    @NotNull
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getClientQueryable7();
    }

    @NotNull
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getClientQueryable7();
    }


    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> asAlias(String alias) {
        super.asAlias(alias);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getClientQueryable7();
    }
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> asTableSegment(BiFunction<String, String, String> segmentAs) {
        super.asTableSegment(segmentAs);
        return getClientQueryable7();
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getClientQueryable7();
    }
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        super.tableLogicDelete(tableLogicDel);
        return getClientQueryable7();
    }
    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        super.configure(configurer);
        return getClientQueryable7();
    }
}
