package com.easy.query.core.proxy;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.impl.SQLDraftAsSelectImpl;
import com.easy.query.core.proxy.impl.SQLNativeDraftImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.impl.SQLSelectAllImpl;
import com.easy.query.core.proxy.impl.SQLSelectAsEntryImpl;
import com.easy.query.core.proxy.impl.SQLSelectIgnoreImpl;
import com.easy.query.core.proxy.impl.SQLSelectKeysImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractBaseProxyEntity<TProxy, TEntity> {


    protected <TPropertyProxy extends SQLColumn<TProxy, TProperty>, TProperty> TPropertyProxy getValueObject(TPropertyProxy propertyProxy) {
        return propertyProxy;
    }

    protected String getValueProperty(String property) {
        return property;
    }

    public void or(SQLActionExpression sqlActionExpression){
        or(true,sqlActionExpression);
    }
    public void or(boolean condition,SQLActionExpression sqlActionExpression){
        if(condition){
            getEntitySQLContext()._whereOr(sqlActionExpression);
        }
    }

    public void and(SQLActionExpression sqlActionExpression){
        and(true,sqlActionExpression);
    }
    public void and(boolean condition,SQLActionExpression sqlActionExpression){
        if(condition){
            getEntitySQLContext()._whereAnd(sqlActionExpression);
        }
    }

    /**
     * 支持where having order
     * @param sqlSegment
     */
    public void executeSQL(String sqlSegment){
        executeSQL(sqlSegment, c->{});
    }

    /**
     * 支持where having order
     *
     * @param sqlSegment
     * @param contextConsume
     */
    public void executeSQL(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume){
        executeSQL(true,sqlSegment,contextConsume);
    }

    /**
     * 支持where having order
     *
     * @param condition
     * @param sqlSegment
     * @param contextConsume
     */
    public void executeSQL(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume){
        if(condition){
            getEntitySQLContext()._executeNativeSql(sqlSegment,contextConsume);
        }
    }


    /**
     * 返回group或者selectDraft自定义sql片段
     * <blockquote><pre>
     * {@code
     *
     *  .select((t, t1, t2) -> new QueryVOProxy() {{
     *      t.sql("now()");
     *      //指定返回类型给draft类型进行明确
     *      //t.sql("now()").setPropertyType(String.class);
     *  }}).toList();
     * }
     * </blockquote></pre>
     * @param sqlSegment
     * @return
     */
    public PropTypeColumn<Object> sql(String sqlSegment) {
        return sql(sqlSegment, c->{});
    }

    /**
     * 返回group或者selectDraft自定义sql片段
     * <blockquote><pre>
     * {@code
     *
     *  .select((t, t1, t2) -> new QueryVOProxy() {{
     *      t.sql("IFNull({0},{1})",c->c.expression(t.id()).value("1"));
     *      //指定返回类型给draft类型进行明确
     *      //t.sql("IFNull({0},{1})",c->c.expression(t.id()).value("1")).setPropertyType(String.class);
     *  }}).toList();
     * }
     * </blockquote></pre>
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    public PropTypeColumn<Object> sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return new SQLNativeDraftImpl((alias, f) -> {
            f.sqlNativeSegment(sqlSegment, c -> {
                if (alias != null) {
                    c.setPropertyAlias(alias);
                }
                contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
            });
        });
    }

    public <TSubQuery> PropTypeColumn<TSubQuery> subQuery(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc) {
        Query<TSubQuery> subQueryQuery = subQueryableFunc.apply();
        return new SQLDraftAsSelectImpl<>((alias, f)->{
            f.columnSubQueryAs(()->subQueryQuery, alias);
        },subQueryQuery.queryClass());
    }

    public SQLSelectAsExpression columnKeys() {
        return new SQLSelectKeysImpl(this.getEntitySQLContext(),getTable());
    }

    public <T> ColumnFuncComparableExpression<T> _now() {
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getEntitySQLContext(),this.getTable(), null, SQLFunc::now);
    }

    public <T> ColumnFuncComparableExpression<T> _utcNow() {
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getEntitySQLContext(),this.getTable(), null, SQLFunc::utcNow);
    }

    public <TRProxy extends ProxyEntity<TRProxy, TREntity>, TREntity> TProxy selectAll(TRProxy proxy) {
        entitySQLContext.accept(new SQLSelectAllImpl(proxy.getEntitySQLContext(),proxy.getTable(), new TablePropColumn[0]));
        return castProxy();
    }
    public <TRProxy extends ProxyEntity<TRProxy, TREntity>, TREntity> TProxy selectIgnores(TablePropColumn... ignoreTableProps) {
        entitySQLContext.accept(new SQLSelectIgnoreImpl(ignoreTableProps));
        return castProxy();
    }

    /**
     * 快速选择表达式
     * @param sqlSelectAsExpression
     */
    public TProxy selectExpression(SQLSelectAsExpression... sqlSelectAsExpression) {
        entitySQLContext.accept(sqlSelectAsExpression);
        return castProxy();
    }
    /**
     * 支持动态select+动态group取列防止sql注入
     * @param sqlTableOwner
     * @param property
     */
    public TProxy selectColumn(SQLTableOwner sqlTableOwner, String property) {
        entitySQLContext.accept(new SQLSelectAsEntryImpl(this.getEntitySQLContext(),sqlTableOwner.getTable(),property));
        return castProxy();
    }
    /**
     * 支持动态select+动态selectAs取列防止sql注入
     * @param sqlTableOwner
     * @param property
     * @param propertyAlias
     */
    public TProxy selectColumnAs(SQLTableOwner sqlTableOwner,String property,String propertyAlias) {
        entitySQLContext.accept(new SQLSelectAsEntryImpl(this.getEntitySQLContext(),sqlTableOwner.getTable(),property,propertyAlias));
        return castProxy();
    }

    private TProxy castProxy(){
        return (TProxy)this;
    }
    public TProxy adapter(Consumer<TProxy> select) {
        select.accept(castProxy());
        return castProxy();
    }

    public void exists(Supplier<Query<?>> subQueryFunc) {
        exists(true, subQueryFunc);
    }

    public void exists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(subQueryFunc.get())));
        }
    }
    public void notExists(Supplier<Query<?>> subQueryFunc) {
        notExists(true, subQueryFunc);
    }

    public void notExists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notExists(subQueryFunc.get())));
        }
    }
}
