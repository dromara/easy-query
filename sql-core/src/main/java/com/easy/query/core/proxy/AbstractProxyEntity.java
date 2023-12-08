package com.easy.query.core.proxy;

import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyEntitySQLContext;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.impl.SQLSelectAllImpl;
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
public abstract class AbstractProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ProxyEntity<TProxy, TEntity>, EntitySQLContextAvailable {

    protected TableAvailable table;
    protected EntitySQLContext entitySQLContext=new ProxyEntitySQLContext();

    protected <TProperty> SQLColumn<TProxy, TProperty> get(String property) {
        return new SQLColumnImpl<>(entitySQLContext,table, property);
    }

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
        return entitySQLContext;
    }


    @Override
    public TProxy create(TableAvailable table,EntitySQLContext entitySQLContext) {
        this.table = table;
        this.entitySQLContext = entitySQLContext;
        return EasyObjectUtil.typeCastNullable(this);
    }


    public SQLSelectAsExpression allFields() {
        return new SQLSelectAllImpl(this.getEntitySQLContext(),getTable(), new TablePropColumn[0]);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public final SQLSelectAsExpression allFieldsExclude(SQLColumn<TProxy, ?>... ignorePropColumns) {
        return new SQLSelectAllImpl(this.getEntitySQLContext(),getTable(), ignorePropColumns);
    }

    public <T> ColumnFuncComparableExpression<T> _now() {
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getEntitySQLContext(),this.getTable(), null, SQLFunc::now);
    }

    public <T> ColumnFuncComparableExpression<T> _utcNow() {
        return new SQLColumnFunctionComparableExpressionImpl<T>(this.getEntitySQLContext(),this.getTable(), null, SQLFunc::utcNow);
    }

    public void or(SQLActionExpression sqlActionExpression){
        or(true,sqlActionExpression);
    }
    public void or(boolean condition,SQLActionExpression sqlActionExpression){
        if(condition){
            getEntitySQLContext()._whereOr(sqlActionExpression);
        }
    }

    public void sqlNativeSegment(String sqlSegment){
        sqlNativeSegment(sqlSegment,c->{});
    }
    public void sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume){
        sqlNativeSegment(true,sqlSegment,contextConsume);
    }

    public void sqlNativeSegment(boolean condition,String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume){
        if(condition){
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            }));
        }
    }


}
