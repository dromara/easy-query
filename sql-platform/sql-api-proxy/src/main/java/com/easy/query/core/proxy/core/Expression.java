package com.easy.query.core.proxy.core;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.extension.casewhen.CaseWhenEntityBuilder;
import com.easy.query.api.proxy.extension.casewhen.CaseWhenThenEntityBuilder;
import com.easy.query.api.proxy.extension.partition.AvgOverBuilder;
import com.easy.query.api.proxy.extension.partition.CountOverBuilder;
import com.easy.query.api.proxy.extension.partition.DenseRankOverBuilder;
import com.easy.query.api.proxy.extension.partition.MaxOverBuilder;
import com.easy.query.api.proxy.extension.partition.MinOverBuilder;
import com.easy.query.api.proxy.extension.partition.RankOverBuilder;
import com.easy.query.api.proxy.extension.partition.RowNumberOverBuilder;
import com.easy.query.api.proxy.extension.partition.SumOverBuilder;
import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLConstantExpression;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.entry.ConcatExpressionSelector;
import com.easy.query.core.proxy.extension.functions.entry.ConcatExpressionSelectorImpl;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableDateTimeChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableStringChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableAnyChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableDateTimeChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionCompareComparableStringChainExpressionImpl;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelector;
import com.easy.query.core.proxy.func.column.ProxyColumnFuncSelectorImpl;
import com.easy.query.core.proxy.impl.SQLConstantExpressionImpl;
import com.easy.query.core.proxy.impl.SQLNativeSegmentExpressionImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * create time 2024/2/17 22:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class Expression {
    private final EntitySQLContext entitySQLContext;

    private Expression(EntitySQLContext entitySQLContext) {

        this.entitySQLContext = entitySQLContext;
    }

    public static Expression of(EntitySQLContext entitySQLContext) {
        return new Expression(entitySQLContext);
    }

    public static Expression of(EntitySQLContextAvailable entitySQLContextAvailable) {
        return new Expression(entitySQLContextAvailable.getEntitySQLContext());
    }

    public ColumnFunctionCompareComparableAnyChainExpression<Object> sqlSegment(String sqlSegment) {
        return sqlSegment(sqlSegment, x -> {
        }, Object.class);
    }

    public ColumnFunctionCompareComparableAnyChainExpression<Object> sqlSegment(String sqlSegment, SQLExpression1<ProxyColumnFuncSelector> contextConsume) {
        return sqlSegment(sqlSegment, contextConsume, Object.class);
    }

    /**
     * 返回group或者selectDraft自定义sql片段
     * <blockquote><pre>
     * {@code
     *
     *  .select((t, t1, t2) -> new QueryVOProxy() {{
     *      t.sqlType("IFNull({0},{1})",c->c.expression(t.id()).value("1"));
     *      //指定返回类型给draft类型进行明确
     *      //t.sqlType("IFNull({0},{1})",c->c.expression(t.id()).value("1")).setPropertyType(String.class);
     *  }}).toList();
     * }
     * </blockquote></pre>
     *
     * @param sqlSegment     片段
     * @param contextConsume 片段参数
     * @param resultClass    类型
     * @param <TR>
     * @return 返回元素sql片段
     */
    public <TR> ColumnFunctionCompareComparableAnyChainExpression<TR> sqlSegment(String sqlSegment, SQLExpression1<ProxyColumnFuncSelector> contextConsume, Class<TR> resultClass) {
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.anySQLFunction(sqlSegment, c -> contextConsume.apply(new ProxyColumnFuncSelectorImpl(c)));
        }, resultClass);
    }

    /**
     * 支持where having order
     *
     * @param sqlSegment
     */
    public void sql(String sqlSegment) {
        sql(sqlSegment, c -> {
        });
    }

    /**
     * 支持where having order
     *
     * @param condition  是否执行
     * @param sqlSegment
     */
    public void sql(boolean condition, String sqlSegment) {
        sql(condition, sqlSegment, c -> {
        });
    }

    /**
     * 支持where having order
     *
     * @param sqlSegment
     * @param contextConsume
     */
    public void sql(String sqlSegment, SQLExpression1<ProxyColumnFuncSelector> contextConsume) {
        sql(true, sqlSegment, contextConsume);
    }

    /**
     * 支持where having order
     *
     * @param condition      是否执行
     * @param sqlSegment
     * @param contextConsume
     */
    public void sql(boolean condition, String sqlSegment, SQLExpression1<ProxyColumnFuncSelector> contextConsume) {
        if (condition) {
            sqlSegment(sqlSegment, contextConsume).executeSQL();
        }
    }

//    public SQLExecutor sqlExecutor(String sqlSegment){
//        return new SQLExecutor(this,sqlSegment);
//    }


    /**
     * 返回group或者selectDraft自定义sql片段
     * <blockquote><pre>
     * {@code
     *
     *  .select((t, t1, t2) -> new QueryVOProxy() {{
     *      t.sqlType("now()");
     *      //指定返回类型给draft类型进行明确
     *      //t.sqlType("now()").setPropertyType(String.class);
     *  }}).toList();
     * }
     * </blockquote></pre>
     *
     * @param sqlSegment
     * @return
     */
    @Deprecated
    public PropTypeColumn<Object> sqlType(String sqlSegment) {
        return sqlType(sqlSegment, c -> {
        });
    }

    /**
     * 返回group或者selectDraft自定义sql片段
     * <blockquote><pre>
     * {@code
     *
     *  .select((t, t1, t2) -> new QueryVOProxy() {{
     *      t.sqlType("IFNull({0},{1})",c->c.expression(t.id()).value("1"));
     *      //指定返回类型给draft类型进行明确
     *      //t.sqlType("IFNull({0},{1})",c->c.expression(t.id()).value("1")).setPropertyType(String.class);
     *  }}).toList();
     * }
     * </blockquote></pre>
     *
     * @param sqlSegment     片段
     * @param contextConsume 片段参数
     * @return 返回元素sql片段
     */
    @Deprecated
    public PropTypeColumn<Object> sqlType(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return sqlType(sqlSegment, contextConsume, Object.class);
    }

    @Deprecated
    public <T> PropTypeColumn<T> sqlType(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume, Class<T> resultClass) {
        return new SQLNativeSegmentExpressionImpl(entitySQLContext, sqlSegment, c -> {
            contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c.getSQLNativeExpressionContext()));
        }).asAnyType(resultClass);
    }


    /**
     * 返回子查询
     * <blockquote><pre>
     * {@code
     *      expression.subQuery(()->{
     *          return easyEntityQuery.queryable(x.class).select(x->new StringProxy(x.id()));
     *      })
     *  }
     * </pre></blockquote>
     *
     * @param subQueryableFunc 创建子查询方法
     * @param <TSubQuery>
     * @return
     */
    public <TSubQuery> ColumnFunctionCompareComparableAnyChainExpression<TSubQuery> subQuery(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc) {
        Query<TSubQuery> subQueryQuery = subQueryableFunc.apply();
        return new ColumnFunctionCompareComparableAnyChainExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.anySQLFunction("{0}", c -> c.subQuery(subQueryQuery));
        }, subQueryQuery.queryClass());
    }


    public ColumnFunctionCompareComparableDateTimeChainExpression<LocalDateTime> now() {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(entitySQLContext, null, null, SQLFunc::now, LocalDateTime.class);
    }

    public ColumnFunctionCompareComparableDateTimeChainExpression<LocalDateTime> utcNow() {
        return new ColumnFunctionCompareComparableDateTimeChainExpressionImpl<>(entitySQLContext, null, null, SQLFunc::utcNow, LocalDateTime.class);
    }

    /**
     * COUNT(*)
     *
     * @return 返回类型为Long
     */
    public ColumnFunctionCompareComparableNumberChainExpression<Long> count() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.count(c -> {
            });
        }, Long.class);
    }

    /**
     * COUNT(*)
     *
     * @return 返回类型为Integer
     */
    public ColumnFunctionCompareComparableNumberChainExpression<Integer> intCount() {
        return new ColumnFunctionCompareComparableNumberChainExpressionImpl<>(entitySQLContext, null, null, f -> {
            return f.count(c -> {
            });
        }, Integer.class);
    }


    /**
     * where exists(....)
     *
     * @param subQueryFunc 子查询创建方法
     */
    public void exists(Supplier<Query<?>> subQueryFunc) {
        exists(true, subQueryFunc);
    }

    /**
     * where exists(....)
     *
     * @param condition    为true是exists生效
     * @param subQueryFunc 子查询创建方法
     */
    public void exists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            entitySQLContext.getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(subQueryFunc.get())));
        }
    }

    /**
     * where not exists(....)
     *
     * @param subQueryFunc 子查询创建方法
     */
    public void notExists(Supplier<Query<?>> subQueryFunc) {
        notExists(true, subQueryFunc);
    }


    /**
     * where exists(....)
     *
     * @param condition    为true是not exists生效
     * @param subQueryFunc 子查询创建方法
     */
    public void notExists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            entitySQLContext.getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notExists(subQueryFunc.get())));
        }
    }

    /**
     * 创建常量值用于比较或者处理
     *
     * @return 数据库常量值构建方法
     */
    public SQLConstantExpression constant() {
        return new SQLConstantExpressionImpl(entitySQLContext);
    }

    public CaseWhenThenEntityBuilder caseWhen(SQLActionExpression sqlActionExpression) {
        return new CaseWhenEntityBuilder(entitySQLContext).caseWhen(sqlActionExpression);
    }

    public <TV, TProperty> ColumnFunctionCompareComparableAnyChainExpression<TProperty> ifElse(SQLActionExpression sqlActionExpression, TV thenValue, TV elseValue) {
        return caseWhen(sqlActionExpression).then(thenValue).elseEnd(elseValue);
    }

    public ColumnFunctionCompareComparableStringChainExpression<String> concat(SQLExpression1<ConcatExpressionSelector> stringExpressions) {
        return new ColumnFunctionCompareComparableStringChainExpressionImpl<>(entitySQLContext, null, null, fx -> {
            return fx.concat(o -> {
                stringExpressions.apply(new ConcatExpressionSelectorImpl(entitySQLContext.getRuntimeContext().fx(), o));
            });
        }, String.class);
    }

    /**
     * <blockquote><pre>
     * {@code
     *  // CONCAT(?,CAST(`id_card` AS SIGNED),?) LIKE ?
     *  Expression expression = s.expression();
     *  SQLConstantExpression constant = expression.constant();
     *  expression.concat(
     *      constant.valueOf(1),
     *      s.idCard().toNumber(Integer.class),
     *      constant.valueOf(2)
     *  ).like(",2,");
     *    }
     * </pre></blockquote>
     *
     * @param expressions 表达式
     * @return
     */
    public ColumnFunctionCompareComparableStringChainExpression<String> concat(PropTypeColumn<?>... expressions) {
        return concat(x -> {
            for (PropTypeColumn<?> expression : expressions) {
                x.expression(EasyObjectUtil.typeCastNullable(expression));
            }
        });
    }


    public RowNumberOverBuilder rowNumberOver() {
        return new RowNumberOverBuilder(entitySQLContext);
    }

    public RankOverBuilder rankOver() {
        return new RankOverBuilder(entitySQLContext);
    }

    public DenseRankOverBuilder denseRankOver() {
        return new DenseRankOverBuilder(entitySQLContext);
    }

    public <TProperty> CountOverBuilder countOver(PropTypeColumn<TProperty> countColumn) {
        return new CountOverBuilder(countColumn, entitySQLContext);
    }

    public <TProperty> SumOverBuilder<TProperty> sumOver(PropTypeColumn<TProperty> countColumn) {
        return new SumOverBuilder<>(countColumn, entitySQLContext);
    }

    public <TProperty> AvgOverBuilder avgOver(PropTypeColumn<TProperty> countColumn) {
        return new AvgOverBuilder(countColumn, entitySQLContext);
    }

    public <TProperty> MaxOverBuilder<TProperty> maxOver(PropTypeColumn<TProperty> countColumn) {
        return new MaxOverBuilder<>(countColumn, entitySQLContext);
    }

    public <TProperty> MinOverBuilder<TProperty> minOver(PropTypeColumn<TProperty> countColumn) {
        return new MinOverBuilder<>(countColumn, entitySQLContext);
    }

    public <TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> EntityQueryable<TProxy, T> subQueryable(Class<T> entityClass) {
        SQLClientApiFactory sqlClientApiFactory = entitySQLContext.getRuntimeContext().getSQLClientApiFactory();
        ClientQueryable<T> queryable = sqlClientApiFactory.createQueryable(entityClass, entitySQLContext.getRuntimeContext());
        TProxy tProxy = EntityQueryProxyManager.create(entityClass);
        EasyEntityQueryable<TProxy, T> tProxyTEasyEntityQueryable = new EasyEntityQueryable<>(tProxy, queryable);
        tProxyTEasyEntityQueryable.get1Proxy().getEntitySQLContext().setContextHolder(this.entitySQLContext.getContextHolder());
        return tProxyTEasyEntityQueryable;
    }
}
