package com.easy.query.core.proxy;

import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.impl.SQLColumnFunctionComparableExpressionImpl;
import com.easy.query.core.proxy.impl.SQLSelectAllImpl;
import com.easy.query.core.proxy.impl.SQLSelectKeysImpl;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
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


    public SQLSelectAsExpression allFields() {
        return new SQLSelectAllImpl(this.getEntitySQLContext(),getTable(), new TablePropColumn[0]);
    }
    public SQLSelectAsExpression keys() {
        return new SQLSelectKeysImpl(this.getEntitySQLContext(),getTable());
    }

    public SQLSelectExpression groupKeys(int index){
        return Select.groupKeys(index);
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

    /**
     * 支持where having order
     * @param sqlSegment
     */
    public void sqlNativeSegment(String sqlSegment){
        sqlNativeSegment(sqlSegment, c->{});
    }

    /**
     * 支持where having order
     *
     * @param sqlSegment
     * @param contextConsume
     */
    public void sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume){
        sqlNativeSegment(true,sqlSegment,contextConsume);
    }

    /**
     * 支持where having order
     *
     * @param condition
     * @param sqlSegment
     * @param contextConsume
     */
    public void sqlNativeSegment(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume){
        if(condition){
            getEntitySQLContext()._nativeSqlSegment(sqlSegment,contextConsume);
        }
    }

}
