package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.select.impl.EasyQueryable3;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLOrderBySelector;
import com.easy.query.api4j.sql.SQLWhereAggregatePredicate;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 */
public abstract class AbstractQueryable2<T1, T2> extends AbstractQueryable<T1> implements Queryable2<T1, T2> {


    protected final ClientQueryable2<T1, T2> entityQueryable2;

    public AbstractQueryable2(ClientQueryable2<T1, T2> entityQueryable2) {
        super(entityQueryable2);
        this.entityQueryable2 = entityQueryable2;
    }

    @Override
    public ClientQueryable2<T1, T2> getClientQueryable2() {
        return entityQueryable2;
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.leftJoin(joinClass, (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> leftJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.leftJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.rightJoin(joinClass, (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> rightJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.rightJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.innerJoin(joinClass, (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    @Override
    public <T3> Queryable3<T1, T2, T3> innerJoin(Queryable<T3> joinQueryable, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.innerJoin(joinQueryable.getClientQueryable(), (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLWherePredicateImpl<>(wherePredicate1), new SQLWherePredicateImpl<>(wherePredicate2), new SQLWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyQueryable3<>(entityQueryable3);
    }

    @Override
    public Queryable2<T1, T2> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return this;
    }

    @Override
    public Queryable2<T1, T2> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public Queryable2<T1, T2> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }


    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression2<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>> selectExpression) {
        ClientQueryable<TR> select = entityQueryable2.select(resultClass, (selector1, selector2) -> {
            selectExpression.apply(new SQLColumnAsSelectorImpl<>(selector1), new SQLColumnAsSelectorImpl<>(selector2));
        });
        return new EasyQueryable<>(select);
    }

    @Override
    public Queryable2<T1, T2> orderByAsc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public Queryable2<T1, T2> orderByDesc(boolean condition, SQLExpression1<SQLOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return this;
    }

    @Override
    public Queryable2<T1, T2> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public Queryable2<T1, T2> having(boolean condition, SQLExpression1<SQLWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return this;
    }

    @Override
    public Queryable2<T1, T2> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public Queryable2<T1, T2> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public Queryable2<T1, T2> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public Queryable2<T1, T2> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public Queryable2<T1, T2> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public Queryable2<T1, T2> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public Queryable2<T1, T2> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public Queryable2<T1, T2> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public Queryable2<T1, T2> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public Queryable2<T1, T2> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public Queryable2<T1, T2> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public Queryable2<T1, T2> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public Queryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public Queryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public Queryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public Queryable2<T1, T2> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public Queryable2<T1, T2> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }

    @Override
    public Queryable2<T1, T2> asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }

}
