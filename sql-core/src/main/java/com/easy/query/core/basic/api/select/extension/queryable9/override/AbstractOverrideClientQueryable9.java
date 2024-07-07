package com.easy.query.core.basic.api.select.extension.queryable9.override;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable9;
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
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends AbstractClientQueryable<T1> {
    protected final Class<T2> t2Class;
    protected final Class<T3> t3Class;
    protected final Class<T4> t4Class;
    protected final Class<T5> t5Class;
    protected final Class<T6> t6Class;
    protected final Class<T7> t7Class;
    protected final Class<T8> t8Class;
    protected final Class<T9> t9Class;

    public AbstractOverrideClientQueryable9(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, Class<T5> t5Class, Class<T6> t6Class, Class<T7> t7Class, Class<T8> t8Class, Class<T9> t9Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
        this.t4Class = t4Class;
        this.t5Class = t5Class;
        this.t6Class = t6Class;
        this.t7Class = t7Class;
        this.t8Class = t8Class;
        this.t9Class = t9Class;
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

    public Class<T8> queryClass8() {
        return t8Class;
    }

    public Class<T9> queryClass9() {
        return t9Class;
    }

    protected abstract ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> getClientQueryable9();

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> cloneQueryable() {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().cloneQueryable(getClientQueryable9());
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getClientQueryable9();
    }

    @Override
    public <TProperty> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> orderByAsc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> orderByDesc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> groupBy(boolean condition, SQLExpression1<ColumnGroupSelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> having(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getClientQueryable9();
    }


    @Override
    public <TProperty> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> include(boolean condition, SQLFuncExpression1<NavigateInclude<T1>, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> distinct(boolean condition) {
        super.distinct(condition);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> disableLogicDelete() {
        super.disableLogicDelete();
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> enableLogicDelete() {
        super.enableLogicDelete();
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> noInterceptor() {
        super.noInterceptor();
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> useInterceptor(String name) {
        super.useInterceptor(name);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> noInterceptor(String name) {
        super.noInterceptor(name);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> useInterceptor() {
        super.useInterceptor();
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> asTracking() {
        super.asTracking();
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> asNoTracking() {
        super.asNoTracking();
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getClientQueryable9();
    }


    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> asAlias(String alias) {
        super.asAlias(alias);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getClientQueryable9();
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getClientQueryable9();
    }
    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        super.tableLogicDelete(tableLogicDel);
        return getClientQueryable9();
    }
    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8,T9> configure(SQLExpression1<ContextConfigurer> configurer) {
        super.configure(configurer);
        return getClientQueryable9();
    }
}
