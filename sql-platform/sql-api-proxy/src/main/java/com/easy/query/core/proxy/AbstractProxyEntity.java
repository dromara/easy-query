package com.easy.query.core.proxy;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLDraftAsSelectImpl;
import com.easy.query.core.proxy.impl.SQLNativeDraftImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

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

    @Override
    public TableAvailable getTable() {
        Objects.requireNonNull(table, "cant found table in sql context");
        return table;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        Objects.requireNonNull(entitySQLContext, "cant found entitySQLContext in sql context");
        return entitySQLContext;
    }


    @Override
    public TProxy create(TableAvailable table,EntitySQLContext entitySQLContext) {
        this.table = table;
        this.entitySQLContext = entitySQLContext;
        return EasyObjectUtil.typeCastNullable(this);
    }


    public void or(SQLActionExpression sqlActionExpression){
        or(true,sqlActionExpression);
    }
    public void or(boolean condition,SQLActionExpression sqlActionExpression){
        if(condition){
            getEntitySQLContext()._whereOr(sqlActionExpression);
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



    public PropTypeColumn<Object> sql(String sqlSegment) {
        return sql(sqlSegment, c->{});
    }

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
}
