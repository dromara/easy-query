package com.easy.query.core.proxy.core;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLConstantExpression;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableDateTimeChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableNumberChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableDateTimeChainExpressionImpl;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparableNumberChainExpressionImpl;
import com.easy.query.core.proxy.impl.SQLConstantExpressionImpl;
import com.easy.query.core.proxy.impl.SQLDraftAsSelectImpl;
import com.easy.query.core.proxy.impl.SQLNativeDraftImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

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

    private Expression(EntitySQLContext entitySQLContext){

        this.entitySQLContext = entitySQLContext;
    }
    public static Expression of(EntitySQLContext entitySQLContext){
        return new Expression(entitySQLContext);
    }
    public static Expression of(EntitySQLContextAvailable entitySQLContextAvailable){
        return new Expression(entitySQLContextAvailable.getEntitySQLContext());
    }



    /**
     * 支持where having order
     * @param sqlSegment
     */
    public void sql(String sqlSegment){
        sql(sqlSegment, c->{});
    }

    /**
     * 支持where having order
     *
     * @param sqlSegment
     * @param contextConsume
     */
    public void sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume){
        sql(true,sqlSegment,contextConsume);
    }

    /**
     * 支持where having order
     *
     * @param condition
     * @param sqlSegment
     * @param contextConsume
     */
    public void sql(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume){
        if(condition){
            entitySQLContext._executeNativeSql(sqlSegment,contextConsume);
        }
    }



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
     * @param sqlSegment
     * @return
     */
    public PropTypeColumn<Object> sqlType(String sqlSegment) {
        return sqlType(sqlSegment, c->{});
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
     * @param sqlSegment 片段
     * @param contextConsume 片段参数
     * @return 返回元素sql片段
     */
    public PropTypeColumn<Object> sqlType(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return new SQLNativeDraftImpl((alias, f) -> {
            f.sqlNativeSegment(sqlSegment, c -> {
                if (alias != null) {
                    c.setPropertyAlias(alias);
                }
                contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
            });
        });
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
     * @param subQueryableFunc 创建子查询方法
     * @return
     * @param <TSubQuery>
     */
    public <TSubQuery> PropTypeColumn<TSubQuery> subQuery(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc) {
        Query<TSubQuery> subQueryQuery = subQueryableFunc.apply();
        return new SQLDraftAsSelectImpl<>((alias, f)->{
            f.columnSubQueryAs(()->subQueryQuery, alias);
        },subQueryQuery.queryClass());
    }


    public ColumnFunctionComparableDateTimeChainExpression<LocalDateTime> now() {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(entitySQLContext,null, null, SQLFunc::now,LocalDateTime.class);
    }

    public ColumnFunctionComparableDateTimeChainExpression<LocalDateTime> utcNow() {
        return new ColumnFunctionComparableDateTimeChainExpressionImpl<>(entitySQLContext,null, null, SQLFunc::utcNow,LocalDateTime.class);
    }
    /**
     * COUNT(*)
     * @return 返回类型为Long
     */
    public ColumnFunctionComparableNumberChainExpression<Long> count() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(entitySQLContext,null,null, f->{
            return f.count(c->{});
        }, Long.class);
    }
    /**
     * COUNT(*)
     * @return 返回类型为Integer
     */
    public ColumnFunctionComparableNumberChainExpression<Integer> intCount() {
        return new ColumnFunctionComparableNumberChainExpressionImpl<>(entitySQLContext,null,null,f->{
            return f.count(c->{});
        }, Integer.class);
    }


    /**
     * where exists(....)
     * @param subQueryFunc 子查询创建方法
     */
    public void exists(Supplier<Query<?>> subQueryFunc) {
        exists(true, subQueryFunc);
    }

    /**
     * where exists(....)
     * @param condition 为true是exists生效
     * @param subQueryFunc 子查询创建方法
     */
    public void exists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            entitySQLContext.accept(new SQLPredicateImpl(f -> f.exists(subQueryFunc.get())));
        }
    }

    /**
     * where not exists(....)
     * @param subQueryFunc 子查询创建方法
     */
    public void notExists(Supplier<Query<?>> subQueryFunc) {
        notExists(true, subQueryFunc);
    }


    /**
     * where exists(....)
     * @param condition 为true是not exists生效
     * @param subQueryFunc 子查询创建方法
     */
    public void notExists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            entitySQLContext.accept(new SQLPredicateImpl(f -> f.notExists(subQueryFunc.get())));
        }
    }
    /**
     * 创建常量值用于比较或者处理
     * @return 数据库常量值构建方法
     */
    public SQLConstantExpression constant(){
        return new SQLConstantExpressionImpl(entitySQLContext);
    }
}
