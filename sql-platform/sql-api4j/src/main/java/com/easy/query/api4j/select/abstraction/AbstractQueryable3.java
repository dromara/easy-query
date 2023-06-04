package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.select.impl.EasyQueryable4;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLColumnResultSelector;
import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.sql.SQLGroupBySelector;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLColumnResultSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLColumnSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLGroupBySelectorImpl;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.lambda.SQLExpression4;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 */
public abstract class AbstractQueryable3<T1, T2, T3> extends AbstractQueryable<T1> implements Queryable3<T1, T2, T3> {


    protected final ClientQueryable3<T1, T2, T3> entityQueryable3;

    public AbstractQueryable3(ClientQueryable3<T1, T2, T3> entityQueryable3) {
        super(entityQueryable3);
        this.entityQueryable3 = entityQueryable3;
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> leftJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.leftJoin(joinClass, (where1, where2, where3, where4) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
        });
        return new EasyQueryable4<>(entityQueryable4);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> leftJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.leftJoin(joinQueryable.getEntityQueryable(), (where1, where2, where3, where4) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
        });
        return new EasyQueryable4<>(entityQueryable4);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> rightJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.rightJoin(joinClass, (where1, where2, where3, where4) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
        });
        return new EasyQueryable4<>(entityQueryable4);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> rightJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.rightJoin(joinQueryable.getEntityQueryable(), (where1, where2, where3, where4) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
        });
        return new EasyQueryable4<>(entityQueryable4);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> innerJoin(Class<T4> joinClass, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.innerJoin(joinClass, (where1, where2, where3, where4) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
        });
        return new EasyQueryable4<>(entityQueryable4);
    }

    @Override
    public <T4> Queryable4<T1, T2, T3, T4> innerJoin(Queryable<T4> joinQueryable, SQLExpression4<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>, SQLWherePredicate<T4>> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = entityQueryable3.innerJoin(joinQueryable.getEntityQueryable(), (where1, where2, where3, where4) -> {
            on.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3), new SQLWherePredicateImpl<>(where4));
        });
        return new EasyQueryable4<>(entityQueryable4);
    }

    @Override
    public Queryable3<T1, T2, T3> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> where(boolean condition, SQLExpression1<SQLWherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> where(boolean condition, SQLExpression3<SQLWherePredicate<T1>, SQLWherePredicate<T2>, SQLWherePredicate<T3>> whereExpression) {
        if (condition) {
            entityQueryable3.where((where1, where2, where3) -> {
                whereExpression.apply(new SQLWherePredicateImpl<>(where1), new SQLWherePredicateImpl<>(where2), new SQLWherePredicateImpl<>(where3));
            });
        }
        return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression3<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>> selectExpression) {
        ClientQueryable<TR> select = entityQueryable3.select(resultClass, (selector1, selector2, selector3) -> {
            selectExpression.apply(new SQLColumnAsSelectorImpl<>(selector1), new SQLColumnAsSelectorImpl<>(selector2), new SQLColumnAsSelectorImpl<>(selector3));
        });
        return new EasyQueryable<>(select);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {
        return entityQueryable3.sumBigDecimalOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {

        return entityQueryable3.sumOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {

        return entityQueryable3.maxOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {

        return entityQueryable3.minOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    @Override
    public <TMember> TMember avgOrDefault(SQLExpression3<SQLColumnResultSelector<T1, TMember>, SQLColumnResultSelector<T2, TMember>, SQLColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {

        return entityQueryable3.avgOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    @Override
    public Integer lenOrDefault(SQLExpression3<SQLColumnResultSelector<T1, ?>, SQLColumnResultSelector<T2, ?>, SQLColumnResultSelector<T3, ?>> columnSelectorExpression, Integer def) {
        return entityQueryable3.lenOrDefault((selector1, selector2, selector3) -> {
            columnSelectorExpression.apply(new SQLColumnResultSelectorImpl<>(selector1), new SQLColumnResultSelectorImpl<>(selector2), new SQLColumnResultSelectorImpl<>(selector3));
        }, def);
    }

    @Override
    public Queryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression1<SQLGroupBySelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<SQLGroupBySelector<T1>, SQLGroupBySelector<T2>, SQLGroupBySelector<T3>> selectExpression) {
        if (condition) {
            entityQueryable3.groupBy((selector1, selector2, selector3) -> {
                selectExpression.apply(new SQLGroupBySelectorImpl<>(selector1), new SQLGroupBySelectorImpl<>(selector2), new SQLGroupBySelectorImpl<>(selector3));
            });
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression1<SQLColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>> selectExpression) {
        if (condition) {
            entityQueryable3.orderByAsc((selector1, selector2, selector3) -> {
                selectExpression.apply(new SQLColumnSelectorImpl<>(selector1), new SQLColumnSelectorImpl<>(selector2), new SQLColumnSelectorImpl<>(selector3));
            });
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression1<SQLColumnSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<SQLColumnSelector<T1>, SQLColumnSelector<T2>, SQLColumnSelector<T3>> selectExpression) {
        if (condition) {
            entityQueryable3.orderByAsc((selector1, selector2, selector3) -> {
                selectExpression.apply(new SQLColumnSelectorImpl<>(selector1), new SQLColumnSelectorImpl<>(selector2), new SQLColumnSelectorImpl<>(selector3));
            });
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> distinct(boolean condition) {
        super.distinct(condition);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> noInterceptor() {
        super.noInterceptor();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> useInterceptor(String name) {
        super.useInterceptor(name);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> noInterceptor(String name) {
        super.noInterceptor(name);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> useInterceptor() {
        super.useInterceptor();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> asTracking() {
        super.asTracking();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> asNoTracking() {
        super.asNoTracking();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> queryLargeColumn(boolean queryLarge) {
        super.queryLargeColumn(queryLarge);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return this;
    }


    @Override
    public Queryable3<T1, T2, T3> asAlias(String alias) {
        super.asAlias(alias);
        return this;
    }
}
