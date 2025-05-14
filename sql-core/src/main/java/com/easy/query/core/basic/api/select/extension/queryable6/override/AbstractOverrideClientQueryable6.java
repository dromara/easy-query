package com.easy.query.core.basic.api.select.extension.queryable6.override;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable6;
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
public abstract class AbstractOverrideClientQueryable6<T1, T2, T3, T4, T5, T6> extends AbstractClientQueryable<T1> {
    protected final Class<T2> t2Class;
    protected final Class<T3> t3Class;
    protected final Class<T4> t4Class;
    protected final Class<T5> t5Class;
    protected final Class<T6> t6Class;

    public AbstractOverrideClientQueryable6(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class,Class<T6> t6Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
        this.t4Class = t4Class;
        this.t5Class = t5Class;
        this.t6Class = t6Class;
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


    protected abstract ClientQueryable6<T1, T2, T3, T4, T5, T6> getClientQueryable6();


    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> cloneQueryable() {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().cloneQueryable(getClientQueryable6());
    }
    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getClientQueryable6();
    }

    @Override
    public <TProperty> ClientQueryable6<T1, T2, T3, T4, T5,T6> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> where(boolean condition, SQLActionExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> orderByAsc(boolean condition, SQLActionExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> orderByDesc(boolean condition, SQLActionExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> groupBy(boolean condition, SQLActionExpression1<ColumnGroupSelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> having(boolean condition, SQLActionExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getClientQueryable6();
    }


    @Override
    public <TProperty> ClientQueryable6<T1, T2, T3, T4, T5,T6> include(boolean condition, SQLFuncExpression1<NavigateInclude, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> distinct(boolean condition) {
        super.distinct(condition);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> disableLogicDelete() {
        super.disableLogicDelete();
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> enableLogicDelete() {
        super.enableLogicDelete();
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> noInterceptor() {
        super.noInterceptor();
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> useInterceptor(String name) {
        super.useInterceptor(name);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> noInterceptor(String name) {
        super.noInterceptor(name);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> useInterceptor() {
        super.useInterceptor();
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> asTracking() {
        super.asTracking();
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> asNoTracking() {
        super.asNoTracking();
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getClientQueryable6();
    }


    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> asAlias(String alias) {
        super.asAlias(alias);
        return getClientQueryable6();
    }
    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getClientQueryable6();
    }
    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> asTableSegment(BiFunction<String, String, String> segmentAs) {
        super.asTableSegment(segmentAs);
        return getClientQueryable6();
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getClientQueryable6();
    }
    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        super.tableLogicDelete(tableLogicDel);
        return getClientQueryable6();
    }
    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5,T6> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        super.configure(configurer);
        return getClientQueryable6();
    }
}
