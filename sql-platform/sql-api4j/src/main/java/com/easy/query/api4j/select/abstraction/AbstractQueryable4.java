package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLColumnSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:38
 */
public abstract class AbstractQueryable4<T1, T2, T3, T4> extends AbstractQueryable<T1> implements Queryable4<T1, T2, T3, T4> {
    protected final ClientQueryable4<T1, T2, T3, T4> entityQueryable4;


    public AbstractQueryable4(ClientQueryable4<T1, T2, T3, T4> entityQueryable4) {
        super(entityQueryable4);
        this.entityQueryable4 = entityQueryable4;
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> getClientQueryable4() {
        return entityQueryable4;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> where(boolean condition, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> whereExpression) {
        if (condition) {
            entityQueryable4.where((where1, where2, where3, where4) -> {
                whereExpression.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
            });
        }
        return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression4<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>, SQLColumnAsSelector<T4, TR>> selectExpression) {
        ClientQueryable<TR> select = entityQueryable4.select(resultClass, (selector1, selector2, selector3, selector4) -> {
            selectExpression.apply(new SQLColumnAsSelectorImpl<>(selector1), new SQLColumnAsSelectorImpl<>(selector2), new SQLColumnAsSelectorImpl<>(selector3), new SQLColumnAsSelectorImpl<>(selector4));
        });
        return new EasyQueryable<>(select);
    }

    @Override
    public Queryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLExpression4<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>, SQLGroupBySelector<T4>> selectExpression) {
        if (condition) {
            entityQueryable4.groupBy((selector1, selector2, selector3, selector4) -> {
                selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3), new SQLGroupBySelectorImpl<>(selector4));
            });
        }
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression1<SQLColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLExpression4<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>, SQLColumnSelector<T4>> selectExpression) {
        if (condition) {
            entityQueryable4.orderByAsc((selector1, selector2, selector3, selector4) -> {
                selectExpression.apply(new SQLColumnSelectorImpl<>(selector1), new SQLColumnSelectorImpl<>(selector2), new SQLColumnSelectorImpl<>(selector3), new SQLColumnSelectorImpl<>(selector4));
            });
        }
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression1<SQLColumnSelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLExpression4<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>, SQLColumnSelector<T4>> selectExpression) {
        if (condition) {
            entityQueryable4.orderByDesc((selector1, selector2, selector3, selector4) -> {
                selectExpression.apply(new SQLColumnSelectorImpl<>(selector1), new SQLColumnSelectorImpl<>(selector2), new SQLColumnSelectorImpl<>(selector3), new SQLColumnSelectorImpl<>(selector4));
            });
        }
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }

    @Override
    public Queryable4<T1, T2, T3, T4> asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }
}
