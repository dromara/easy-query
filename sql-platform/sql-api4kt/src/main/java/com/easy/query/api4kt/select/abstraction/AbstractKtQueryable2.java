package com.easy.query.api4kt.select.abstraction;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.KtQueryable2;
import com.easy.query.api4kt.select.KtQueryable3;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable3;
import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtGroupBySelector;
import com.easy.query.api4kt.sql.SQLKtOrderBySelector;
import com.easy.query.api4kt.sql.SQLKtWhereAggregatePredicate;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtColumnAsSelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtGroupBySelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtOrderByColumnSelectorImpl;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
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
public abstract class AbstractKtQueryable2<T1, T2> extends AbstractKtQueryable<T1> implements KtQueryable2<T1, T2> {


    protected final ClientQueryable2<T1, T2> entityQueryable2;

    public AbstractKtQueryable2(ClientQueryable2<T1, T2> entityQueryable2) {
        super(entityQueryable2);
        this.entityQueryable2 = entityQueryable2;
    }

    @Override
    public ClientQueryable2<T1, T2> getClientQueryable2() {
        return entityQueryable2;
    }

    @Override
    public <T3> KtQueryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.leftJoin(joinClass, (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    @Override
    public <T3> KtQueryable3<T1, T2, T3> leftJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.leftJoin(joinQueryable.getEntityQueryable(), (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    @Override
    public <T3> KtQueryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.rightJoin(joinClass, (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    @Override
    public <T3> KtQueryable3<T1, T2, T3> rightJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.rightJoin(joinQueryable.getEntityQueryable(), (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    @Override
    public <T3> KtQueryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.innerJoin(joinClass, (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    @Override
    public <T3> KtQueryable3<T1, T2, T3> innerJoin(KtQueryable<T3> joinQueryable, SQLExpression3<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>, SQLKtWherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = entityQueryable2.innerJoin(joinQueryable.getEntityQueryable(), (wherePredicate1, wherePredicate2, wherePredicate3) -> {
            on.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2), new SQLKtWherePredicateImpl<>(wherePredicate3));
        });
        return new EasyKtQueryable3<>(entityQueryable3);
    }

    @Override
    public KtQueryable2<T1, T2> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> where(boolean condition, SQLExpression2<SQLKtWherePredicate<T1>, SQLKtWherePredicate<T2>> whereExpression) {
        if (condition) {
            entityQueryable2.where((wherePredicate1, wherePredicate2) -> {
                whereExpression.apply(new SQLKtWherePredicateImpl<>(wherePredicate1), new SQLKtWherePredicateImpl<>(wherePredicate2));
            });
        }
        return this;
    }


    @Override
    public <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression2<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>> selectExpression) {
        ClientQueryable<TR> select = entityQueryable2.select(resultClass, (selector1, selector2) -> {
            selectExpression.apply(new SQLKtColumnAsSelectorImpl<>(selector1), new SQLKtColumnAsSelectorImpl<>(selector2));
        });
        return new EasyKtQueryable<>(select);
    }

    @Override
    public KtQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>> selectExpression) {
        if (condition) {
            entityQueryable2.orderByAsc((selector1, selector2) -> {
                selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2));
            });
        }
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression1<SQLKtOrderBySelector<T1>> selectExpression) {
        super.orderByDesc(condition, selectExpression);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<SQLKtOrderBySelector<T1>, SQLKtOrderBySelector<T2>> selectExpression) {
        if (condition) {
            entityQueryable2.orderByDesc((selector1, selector2) -> {
                selectExpression.apply(new SQLKtOrderByColumnSelectorImpl<>(selector1), new SQLKtOrderByColumnSelectorImpl<>(selector2));
            });
        }
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> groupBy(boolean condition, SQLExpression1<SQLKtGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> groupBy(boolean condition, SQLExpression2<SQLKtGroupBySelector<T1>, SQLKtGroupBySelector<T2>> selectExpression) {
        if (condition) {
            entityQueryable2.groupBy((selector1, selector2) -> {
                selectExpression.apply(new SQLKtGroupBySelectorImpl<>(selector1), new SQLKtGroupBySelectorImpl<>(selector2));
            });
        }
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> having(boolean condition, SQLExpression1<SQLKtWhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }

    @Override
    public KtQueryable2<T1, T2> asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }

}
