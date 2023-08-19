package com.easy.query.api4kt.select.abstraction;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable3;
import com.easy.query.api4kt.select.KtQueryable4;
import com.easy.query.api4kt.select.extension.queryable3.override.AbstractOverrideKtQueryable3;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable4;
import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtColumnAsSelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 */
public abstract class AbstractKtQueryable3<T1, T2, T3> extends AbstractOverrideKtQueryable3<T1, T2, T3> implements KtQueryable3<T1, T2, T3> {


    protected final ClientQueryable3<T1, T2, T3> entityQueryable3;

    public AbstractKtQueryable3(ClientQueryable3<T1, T2, T3> entityQueryable3) {
        super(entityQueryable3);
        this.entityQueryable3 = entityQueryable3;
    }

    @Override
    public KtQueryable3<T1, T2, T3> getQueryable3() {
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> getClientQueryable3() {
        return entityQueryable3;
    }

    @Override
    public <T4> KtQueryable4<T1, T2, T3, T4> leftJoin(Class<T4> joinClass, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.leftJoin(joinClass, (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    @Override
    public <T4> KtQueryable4<T1, T2, T3, T4> leftJoin(KtQueryable<T4> joinQueryable, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.leftJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    @Override
    public <T4> KtQueryable4<T1, T2, T3, T4> rightJoin(Class<T4> joinClass, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.rightJoin(joinClass, (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    @Override
    public <T4> KtQueryable4<T1, T2, T3, T4> rightJoin(KtQueryable<T4> joinQueryable, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.rightJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    @Override
    public <T4> KtQueryable4<T1, T2, T3, T4> innerJoin(Class<T4> joinClass, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.innerJoin(joinClass, (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    @Override
    public <T4> KtQueryable4<T1, T2, T3, T4> innerJoin(KtQueryable<T4> joinQueryable, SQLExpression4<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>, SQLKtWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.innerJoin(joinQueryable.getClientQueryable(), (where1, where2, where3, where4) -> {
            on.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3), new SQLKtWherePredicateImpl<>(where4));
        });
        return new EasyKtQueryable4<>(entityQueryable4);
    }

    @Override
    public KtQueryable3<T1, T2, T3> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> where(boolean condition, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> whereExpression) {
        if (condition) {
            entityQueryable3.where((where1, where2, where3) -> {
                whereExpression.apply(new SQLKtWherePredicateImpl<>(where1), new SQLKtWherePredicateImpl<>(where2), new SQLKtWherePredicateImpl<>(where3));
            });
        }
        return this;
    }

    @Override
    public <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression3<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>, SQLKtColumnAsSelector<T3, TR>> selectExpression) {
        ClientQueryable<TR> select = entityQueryable3.select(resultClass, (selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLKtColumnAsSelectorImpl<>(selector1), new SQLKtColumnAsSelectorImpl<>(selector2), new SQLKtColumnAsSelectorImpl<>(selector3));
        });
        return new EasyKtQueryable<>(select);
    }

    @Override
    public KtQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>, SQLKtGroupBySelector<T3>> selectExpression) {
        if (condition) {
            entityQueryable3.groupBy((selector1, selector2, selector3) -> {
                selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2), new SQLKtGroupBySelectorImpl<>(selector3));
            });
        }
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>> selectExpression) {
        if (condition) {
            entityQueryable3.orderByAsc((selector1, selector2, selector3) -> {
                selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3));
            });
        }
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>, SQLKtOrderBySelector<T3>> selectExpression) {
        if (condition) {
            entityQueryable3.orderByDesc((selector1, selector2, selector3) -> {
                selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2), new SQLKtOrderByColumnSelectorImpl<>(selector3));
            });
        }
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> orderByObject(boolean condition, ObjectSort configuration) {
        super.orderByObject(condition,configuration);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtQueryable3<T1, T2, T3> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }


    @Override
    public KtQueryable3<T1, T2, T3> asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }
    @Override
    public KtQueryable3<T1, T2, T3> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return this;
    }
}
